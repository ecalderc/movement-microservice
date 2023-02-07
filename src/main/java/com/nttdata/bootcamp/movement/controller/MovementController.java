package com.nttdata.bootcamp.movement.controller;

import com.nttdata.bootcamp.movement.entity.Movement;
import com.nttdata.bootcamp.movement.service.MovementService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/movement")
public class MovementController {

    @Autowired
    private MovementService movementService;

    //Movement search
    @GetMapping("/findAll")
    public Flux<Movement> findAllMovements() {
        return movementService.findAll();
    }

    //Find for movement by account number
    @GetMapping("/findByAccountNumber/{accountNumber}")
    public Flux<Movement> findByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        return movementService.findByAccountNumber(accountNumber);
    }

    //Find for movement by movement number
    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @GetMapping("/findByMovementNumber/{numberMovement}")
    public Mono<Movement> findByMovementNumber(@PathVariable("movementNumber") String movementNumber) {
        return movementService.findByMovementNumber(movementNumber);
    }

    @PostMapping(value = "/saveTransactionOrigin")
    public Mono<Movement> saveTransactionOrigin(@RequestBody Movement dataMovement) {
        return movementService.saveMovement(dataMovement);
    }

    @PostMapping(value = "/saveTransactionDestination")
    public Mono<Movement> saveTransactionDestination(@RequestBody Movement dataMovement) {
        return movementService.saveMovement(dataMovement);
    }

    //Update number of movements
    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @PutMapping("/updateMovements")
    public Mono<Movement> updateMovements(@RequestBody Movement dataMovement) {
        return movementService.updateMovements(dataMovement);
    }

    //Update commission
    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @PutMapping(value = "/updateCommission")
    public Mono<Movement> updateCommission(@RequestBody Movement dataMovement) {
        return movementService.updateCommission(dataMovement);
    }

    //Delete movement
    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @DeleteMapping("/delete/{numberMovement}")
    public Mono<Movement> deleteMovement(@PathVariable("numberMovement") String numberMovement) {
        return movementService.deleteMovement(numberMovement);
    }

    private Mono<String> fallBackGetMovement(Exception e) {
        return Mono.just("Movement Microservice is not responding");
    }

}
