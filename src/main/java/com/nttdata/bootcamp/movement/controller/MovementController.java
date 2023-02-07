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
    @GetMapping("/findAll")
    public Flux<Movement> findAllMovements() {
        return movementService.findAll();
    }

    //Find for movement by account number
    @GetMapping("/findByAccountNumber/{accountNumber}")
    public Flux<Movement> findAllMovementsByNumber(@PathVariable("accountNumber") String accountNumber) {
        return movementService.findByAccountNumber(accountNumber);
    }

    //Find for movement by movement number
    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @GetMapping("/findByMovementNumber/{numberMovement}")
    public Mono<Movement> findByMovementNumber(@PathVariable("movementNumber") String movementNumber) {
        return movementService.findByMovementNumber(movementNumber);
    }

    @CircuitBreaker(name = "movement", fallbackMethod = "fallBackGetMovement")
    @PostMapping(value = "/updateCommission")
    public Mono<Movement> updateCommission(@PathVariable("numberTransaction") String numberTransaction, @PathVariable("commission") Double commission) {
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
