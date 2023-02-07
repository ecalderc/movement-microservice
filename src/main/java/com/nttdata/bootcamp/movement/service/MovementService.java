package com.nttdata.bootcamp.movement.service;

import com.nttdata.bootcamp.movement.entity.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface Service
public interface MovementService {

    public Flux<Movement> findAll();

    public Flux<Movement> findByAccountNumber(String accountNumber);

    public Mono<Movement> findByMovementNumber(String movementNumber);

    public Flux<Movement> findCommissionByAccountNumber(String accountNumber);

    public Mono<Movement> saveMovement(Movement movement);

    public Mono<Movement> updateMovement(Movement movement);

    public Mono<Movement> deleteMovement(String accountNumber);

}
