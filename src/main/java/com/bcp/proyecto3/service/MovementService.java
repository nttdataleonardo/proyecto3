package com.bcp.proyecto3.service;

import com.bcp.proyecto3.model.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Flux<Movement> getAllMovements();
    Mono<Movement> createMovement(Movement Movement);
    Mono<Movement> updateMovement(String id, Movement updatedMovement);
    Mono<Movement> getMovementById(String id);
    Mono<Movement> deleteMovementById(String id);
}
