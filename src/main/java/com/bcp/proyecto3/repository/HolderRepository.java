package com.bcp.proyecto3.repository;

import com.bcp.proyecto3.model.Holder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Document(collection = "holders")
public interface HolderRepository extends ReactiveMongoRepository<Holder, String> {
}
