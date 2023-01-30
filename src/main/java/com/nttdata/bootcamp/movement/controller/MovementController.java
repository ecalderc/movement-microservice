package com.nttdata.bootcamp.movement.controller;

import com.nttdata.bootcamp.movement.entity.Movement;
import com.nttdata.bootcamp.movement.service.MovementService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/movement")
public class MovementController {

    @Autowired
    private MovementService movementService;

    //Movement search
    @GetMapping("/findAllMovements")
    public Flux<Movement> findAllMovements() {
        Flux<Movement> movementsFlux = movementService.findAll();
        log.info("Registered movements: " + movementsFlux);
        return movementsFlux;
    }

    //Movement by AccountNumber
    @GetMapping("/findAllMovementsByNumber/{accountNumber}")
    public Flux<Movement> findAllMovementsByNumber(@PathVariable("accountNumber") String accountNumber) {
        Flux<Movement> movementsFlux = movementService.findByAccountNumber(accountNumber);
        log.info("Registered movements of account number: " + accountNumber + "-" + movementsFlux);
        return movementsFlux;
    }

    //Movement  by transactionNumber
    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @GetMapping("/findByMovementNumber/{numberMovement}")
    public Mono<Movement> findByMovementNumber(@PathVariable("numberMovement") String numberMovement) {
        log.info("Searching Movement by number: " + numberMovement);
        return movementService.findByNumber(numberMovement);
    }

    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @PostMapping(value = "/updateCommission")
    public Mono<Movement> updateCommission(@PathVariable("numberTransaction") String numberTransaction,
                                           @PathVariable("commission") Double commission) {
        Mono<Movement> movementMono = findByMovementNumber(numberTransaction);
        movementMono.block().setCommission(commission);
        Mono<Movement> movementsMono = movementService.saveMovement(movementMono.block());
        return movementsMono;
    }

    @PostMapping(value = "/saveTransactionOrigin")
    public Mono<Movement> saveTransactionOrigin(@RequestBody Movement dataMovement) {
        Mono.just(dataMovement).doOnNext(t -> {

                    t.setCreationDate(new Date());
                    t.setModificationDate(new Date());
                    t.setTypeTransaction("Transfer");

                }).onErrorReturn(dataMovement).onErrorResume(e -> Mono.just(dataMovement))
                .onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> log.info(x.toString()));

        Mono<Movement> movementsMono = movementService.saveMovement(dataMovement);
        return movementsMono;
    }

    @PostMapping(value = "/saveTransactionDestination")
    public Mono<Movement> saveTransactionDestination(@RequestBody Movement dataMovement) {
        Mono.just(dataMovement).doOnNext(t -> {

                    t.setCreationDate(new Date());
                    t.setModificationDate(new Date());
                    t.setTypeTransaction("Transfer");


                }).onErrorReturn(dataMovement).onErrorResume(e -> Mono.just(dataMovement))
                .onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> log.info(x.toString()));

        Mono<Movement> movementsMono = movementService.saveMovement(dataMovement);
        return movementsMono;
    }

    //Update Movement
    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @PutMapping("/updateMovements/{numberMovement}")
    public Mono<Movement> updateMovements(@PathVariable("numberTransfer") String numberMovements,
                                          @Valid @RequestBody Movement dataMovement) {
        Mono.just(dataMovement).doOnNext(t -> {

                    t.setMovementNumber(numberMovements);
                    t.setModificationDate(new Date());

                }).onErrorReturn(dataMovement).onErrorResume(e -> Mono.just(dataMovement))
                .onErrorMap(f -> new InterruptedException(f.getMessage())).subscribe(x -> log.info(x.toString()));

        Mono<Movement> updateTransfer = movementService.updateMovement(dataMovement);
        return updateTransfer;
    }

    //Delete Movement
    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @DeleteMapping("/deleteMovement/{numberMovement}")
    public Mono<Void> deleteMovement(@PathVariable("numberTransaction") String numberMovement) {
        log.info("Deleting Movement by number: " + numberMovement);
        Mono<Void> delete = movementService.deleteMovement(numberMovement);
        return delete;

    }

    private Mono<Movement> fallBackGetMovement(Exception e) {
        Movement movement = new Movement();
        Mono<Movement> movementsMono = Mono.just(movement);
        return movementsMono;
    }

}
