package com.bcp.proyecto3.serviceimpl;
 
import com.bcp.proyecto3.exceptions.ConflictException;
import com.bcp.proyecto3.exceptions.NotFoundException;
import com.bcp.proyecto3.model.Movement;
import com.bcp.proyecto3.model.Movement;
import com.bcp.proyecto3.repository.MovementRepository;
import com.bcp.proyecto3.service.MovementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;

    @Autowired
    public MovementServiceImpl(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Override
    public Flux<Movement> getAllMovements() {
        return this.movementRepository.findAll().map(Movement::toMovement);
    }
    @Override
    public Mono<Movement> createMovement(Movement movement) {
        return movementRepository.findById(movement.getId())
                .flatMap(movementEntity ->
                        Mono.error(new ConflictException("Movement Code already exists: " + movement.getId()))
                ).then(Mono.just(movement.toMovement()))
                .flatMap(this.movementRepository::save)
                .map(Movement::toMovement);
    }
    @Override
    public Mono<Movement> updateMovement(String id, Movement updatedMovement) {
        return this.movementRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Movement: " + id)))
                .flatMap(movementEntity -> {
                    BeanUtils.copyProperties(movementRepository, movementEntity, "created", "isDeleted");
                    return Mono.just(movementEntity);
                })
                .flatMap(this.movementRepository::save)
                .map(Movement::toMovement);
    }
    @Override
    public Mono<Movement> getMovementById(String id) {
        return movementRepository.findById(id)
                .switchIfEmpty(Mono.error( new ConflictException("Movement does not exist : " + id)))
                .map(Movement::toMovement);
    }
    @Override
    public Mono<Movement> deleteMovementById(String id) {
        return  movementRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Non existent Movement: " + id)))
                .flatMap(movementEntity -> {
                    return movementRepository.deleteById(movementEntity.getId())
                            .thenReturn(movementEntity.toMovement());
                });
    }

}
