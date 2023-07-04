package com.bcp.proyecto3.repository;


import com.bcp.proyecto3.model.AuthorizedSigner;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Document(collection = "autorizedsigners")
public interface AuthorizedSignerRepository extends ReactiveMongoRepository<AuthorizedSigner, String> {
}
