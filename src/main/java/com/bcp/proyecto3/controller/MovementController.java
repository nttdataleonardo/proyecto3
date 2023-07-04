package com.bcp.proyecto3.controller;

import com.bcp.proyecto3.model.Movement;
import com.bcp.proyecto3.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movements")
public class MovementController {
    @Autowired
    private MovementService movementService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Movement> getAllUsers() {
        return movementService.getAllMovements();
    }

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Movement> createmovement(@RequestBody Movement movement) {
        return movementService.createMovement(movement);
    }

    @PutMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Movement> updatemovement(@PathVariable String id,@RequestBody Movement updatedmovement) {
        return movementService.updateMovement(id,updatedmovement);
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<Movement> getmovementById(@PathVariable String id) {
        return movementService.getMovementById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Movement> deletemovementById(@PathVariable String id) {
        return movementService.deleteMovementById(id);
    }

}
