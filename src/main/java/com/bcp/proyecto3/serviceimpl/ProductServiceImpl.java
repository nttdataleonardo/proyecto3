package com.bcp.proyecto3.serviceimpl;
 
import com.bcp.proyecto3.exceptions.ConflictException;
import com.bcp.proyecto3.exceptions.NotFoundException;
import com.bcp.proyecto3.model.*;
import com.bcp.proyecto3.repository.ProductRepository;
import com.bcp.proyecto3.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll().map(Product::toProduct);
    }
    @Override
    public Mono<Product> createProduct(Product product) {
        return clientService.getClientById(product.getIdClient())
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent ProductId: " + product.getIdClient())))
                .flatMap(client -> {
                    return validateClientAndProduct(client, product);
                });
    }
    @Override
    public Mono<Product> validateClientAndProduct(Client client, Product product) {
        ProductType[] typesToPersonal = {ProductType.AHORRO, ProductType.CUENTA_CORRIENTE, ProductType.PLAZO_FIJO};
        ProductType[] typesToBusiness = {ProductType.AHORRO, ProductType.PLAZO_FIJO};
        if (ClientType.PERSONAL.equals(client.getType())) {
            return existsProductByTypes(product, typesToPersonal,"Un cliente personal solo puede tener un máximo de una cuenta de ahorro, una cuenta corriente o cuentas a plazo fijo");
        } else if (ClientType.BUSINESS.equals(client.getType())){
            return existsProductByTypes(product, typesToBusiness,"Un cliente empresarial no puede tener una cuenta de ahorro o de plazo fijo, pero sí múltiples cuentas corrientes");
        }  else if (ClientType.PERSONAL_VIP.equals(client.getType())){
            return Mono.empty();
        }  else if (ClientType.BUSINESS_PYME.equals(client.getType())){
            return Mono.empty();
        }  else {
            return Mono.empty();
        }
    }

    public Mono<Product> existsProductByTypes(Product product, ProductType[] typesProduct, String errorMessage) {
        Mono<Product> productsFound = productRepository.existsByClientIdAndTypeContains(product.getIdClient(), typesProduct);
        return productRepository.existsByClientIdAndTypeContains(product.getIdClient(), typesProduct)
                .flatMap(productEntity ->
                        Mono.error(new ConflictException(errorMessage))
                )
                .switchIfEmpty(Mono.just(product))
                .then(Mono.just(product.toProduct()))
                .flatMap(this::saveProduct);
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Product> updateProduct(String id, Product updatedProduct) {
        return this.productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Product: " + id)))
                .flatMap(productEntity -> {
                    BeanUtils.copyProperties(productRepository, productEntity, "created", "isDeleted");
                    return Mono.just(productEntity);
                })
                .flatMap(this.productRepository::save)
                .map(Product::toProduct);
    }
    @Override
    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error( new ConflictException("Product does not exist : " + id)))
                .map(Product::toProduct);
    }
    @Override
    public Mono<Product> deleteProductById(String id) {
        return  productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Product: " + id)))
                .flatMap(productEntity -> {
                    return productRepository.deleteById(productEntity.getId())
                            .thenReturn(productEntity.toProduct());
                });
    }

    @Override
    public Mono<Product> depositProduct(String id, Product updatedProduct) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Product: " + id)))
                .flatMap(productEntity -> {
                    productEntity.setBalance(productEntity.getBalance().add(updatedProduct.getBalance()));
                    return productRepository.save(productEntity)
                            .thenReturn(productEntity.toProduct());
                });
    }

    @Override
    public Mono<Product> withdrawal(String id, Product updatedProduct) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Product: " + id)))
                .flatMap(productEntity -> {
                    productEntity.setBalance(productEntity.getBalance().subtract(updatedProduct.getBalance()));
                    return productRepository.save(productEntity)
                            .thenReturn(productEntity.toProduct());
                });
    }

    @Override
    public Mono<Product> paycredit(String id, Product product) {
        return  productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Product: " + id)))
                .flatMap(productEntity -> {
                    BigDecimal montocredito = productEntity.getBalance();
                    productEntity.setBalance(montocredito.add(product.getPaycredit()));
                    return productRepository.save(productEntity)
                            .thenReturn(productEntity.toProduct());
                });
    }

    @Override
    public Mono<Product> chargeconsumptions(String id, Product product) {
        ProductType[] typesCredit = {ProductType.TARJETA_CREDITO_PERSONAL, ProductType.TARJETA_CREDITO_EMPRESARIAL};
        Mono<Product> productsFound = productRepository.existsByIdAndTypeContains(id, typesCredit);
        return  productsFound
                .switchIfEmpty(Mono.error(new ConflictException("No es una cuenta de crédito")))
                .flatMap(productEntity -> {
                    BigDecimal saldodisponible = productEntity.getBalance();
                    BigDecimal consumo = product.getPaycredit(); //product.getPaycredit() = consumo por el cliente, balance usado para el flujo de dinero que fluctua siendo el reflejo del credito
                    BigDecimal balanceUpdate = saldodisponible.subtract(consumo);
                    compareToAmount(saldodisponible,consumo).subscribe(result->{
                        if(result){
                            productEntity.setBalance(saldodisponible);
                        }else {
                            productEntity.setBalance(balanceUpdate);
                        }
                    });
                    return productRepository.save(productEntity)
                            .thenReturn(productEntity.toProduct());
                });
    }

    public Mono<Boolean> compareToAmount(BigDecimal monto1, BigDecimal monto2) {
        return Mono.just(monto1)
                .flatMap(m1 -> Mono.just(monto1).map(m2 -> m1.compareTo(monto2) < 0))
                .defaultIfEmpty(false);
    }

    @Override
    public Flux<Product> availablebalances(String id){
        ProductType[] typesCreditsAndAccountBank = { ProductType.AHORRO, ProductType.CUENTA_CORRIENTE, ProductType.PLAZO_FIJO,
                ProductType.TARJETA_CREDITO_PERSONAL, ProductType.TARJETA_CREDITO_EMPRESARIAL};
        return this.productRepository.existsByClientIdAndTypeContainsAll(id,typesCreditsAndAccountBank).map(Product::toProduct);
    }
}
