package com.bcp.proyecto3.repository;

import com.bcp.proyecto3.model.Product;
import com.bcp.proyecto3.model.ProductType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Repository
@Document(collection = "products")
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    @Query("{'idClient': ?0}")
    Flux<Product> findByClientId(String idClient);

    @Query("{'idClient': ?0, 'type': { $in: ?1 }}")
    Mono<Product> existsByClientIdAndTypeContains(String idClient, ProductType[] types);

    @Query("{'id': ?0, 'type': { $in: ?1 }}")
    Mono<Product> existsByIdAndTypeContains(String idProduct, ProductType[] types);

    @Query("{'idClient': ?0, 'type': { $in: ?1 }}")
    Flux<Product> existsByClientIdAndTypeContainsAll(String idClient, ProductType[] types);

}