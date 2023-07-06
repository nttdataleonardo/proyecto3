package com.bcp.proyecto3.service;


import com.bcp.proyecto3.model.Client;
import com.bcp.proyecto3.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Flux<Product> getAllProducts();
    Mono<Product> createProduct(Product product);
    Mono<Product> validateClientAndProduct(Client client, Product product);
    Mono<Product> saveProduct(Product product);
    Mono<Product> updateProduct(String id, Product updatedProduct);
    Mono<Product> getProductById(String id);
    Mono deleteProductById(String id);

    Mono<Product> depositProduct(String id, Product updatedProduct);
    Mono<Product> withdrawal(String id, Product updatedProduct);
    Mono<Product> paycredit(String id, Product updatedProduct);
    //Maybe<Product> paytotalcredit(String id, Product product);
    Mono<Product> chargeconsumptions(String id, Product product);

    Flux<Product> availablebalances(String idClient);
    Mono<Product> transferBetweenAccounts(String id, Product product);

}
