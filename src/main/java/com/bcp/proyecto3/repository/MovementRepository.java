package com.bcp.proyecto3.repository;

import com.bcp.proyecto3.model.Movement;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Document(collection = "movements")
public interface MovementRepository extends ReactiveMongoRepository<Movement, String> {
}
