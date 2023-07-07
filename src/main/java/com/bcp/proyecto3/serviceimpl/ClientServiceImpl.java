package com.bcp.proyecto3.serviceimpl;

import com.bcp.proyecto3.CacheConfig;
import com.bcp.proyecto3.dto.ClientSummaryDto;
import com.bcp.proyecto3.exceptions.ConflictException;
import com.bcp.proyecto3.exceptions.NotFoundException;
import com.bcp.proyecto3.model.Client;
import com.bcp.proyecto3.model.Product;
import com.bcp.proyecto3.model.ProductType;
import com.bcp.proyecto3.repository.ClientRepository;
import com.bcp.proyecto3.repository.ProductRepository;
import com.bcp.proyecto3.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Autowired(required = false)
    private ReactiveRedisOperations<String, Client> redisTemplate;

    @Autowired
    public ClientServiceImpl(ClientRepository ClientRepository, ProductRepository productRepository ) {
        this.clientRepository = ClientRepository;
        this.productRepository = productRepository;

    }


    public Flux<Client> getAllClients() {
        return clientRepository.findAll().map(Client::toClient);
    }
    @Override
    public Mono<Client> createClient(Client client) {
        return clientRepository.findById(client.getId())
                .flatMap(clientEntity ->
                        Mono.error(new ConflictException("Client Code already exists: " + client.getId()))
                ).then(Mono.just(client.toClient()))
                .flatMap(this.clientRepository::save)
                .map(Client::toClient);
    }
    @Override
    public Mono<Client> updateClient(String id, Client updatedClient) {
        return this.clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Client: " + id)))
                .flatMap(clientEntity -> {
                    BeanUtils.copyProperties(clientRepository, clientEntity, "created", "isDeleted");
                    return Mono.just(clientEntity);
                })
                .flatMap(this.clientRepository::save)
                .map(Client::toClient);
    }
    //@Cacheable(value = "itemCache")
    @Override
    public Mono<Client> getClientById(String id) {
        return /*redisTemplate.opsForValue().get(id)
                .switchIfEmpty(Mono.error(new ConflictException("Client does not exist: " + id)));*/
      clientRepository.findById(id)
                .switchIfEmpty(Mono.error( new ConflictException("Client does not exist : " + id)))
                .map(Client::toClient);
    }
    @Override
    public Mono<Client> deleteClientById(String id) {
        return  clientRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Client: " + id)))
                .flatMap(clientEntity -> {
                    return clientRepository.deleteById(clientEntity.getId())
                            .thenReturn(clientEntity.toClient());
                });
    }

    @Override
    public Mono<ClientSummaryDto> generateSummary(String clienteId) {
        return clientRepository.findById(clienteId)
                .flatMap(cliente -> {
                    ClientSummaryDto resumenCliente = new ClientSummaryDto();
                    resumenCliente.setClientId(cliente.getId());
                    resumenCliente.setClientName(cliente.getName());

                    return productRepository.findByClientId(clienteId)
                            .collectList()
                            .map(productos -> {
                                List<ProductType> tiposProductos = productos.stream()
                                        .map(Product::getType)
                                        .collect(Collectors.toList());

                                BigDecimal saldoTotal = productos.stream()
                                        .map(Product::getBalance)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                BigDecimal creditoDisponibleTotal = productos.stream()
                                        .map(Product::getCredit)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                resumenCliente.setTypeProducts(tiposProductos);
                                resumenCliente.setTotalBalance(saldoTotal);
                                resumenCliente.setCreditAvailableTotal(creditoDisponibleTotal);

                                return resumenCliente;
                            });
                });
    }
}
