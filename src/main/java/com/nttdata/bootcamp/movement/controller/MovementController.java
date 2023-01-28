package com.nttdata.bootcamp.movement.controller;

import com.nttdata.bootcamp.movement.entity.Movement;
import com.nttdata.bootcamp.movement.service.MovementService;
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

    @GetMapping
    public Flux<Movement> findAll() {
        return movementService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Movement> findById(@PathVariable String id) {
        return movementService.findById(id);
    }

    @PostMapping("/currentAccount")
    public Mono<Movement> saveTransactionByCurrentAccount(@RequestBody Movement transaction) {
        return movementService.saveTransactionOfCurrentAccount(transaction);
    }

    @PostMapping("/savingAccount")
    public Mono<Movement> saveTransactionBySavingAccount(@RequestBody Movement transaction) {
        return movementService.saveTransactionOfSavingAccount(transaction);
    }

    @PostMapping("/fixedTermAccount")
    public Mono<Movement> saveTransactionByFixedTermAccount(@RequestBody Movement transaction) {
        return movementService.saveTransactionOfFixedTermAccount(transaction);
    }

    @PutMapping("/{id}")
    public Mono<Movement> update(@RequestBody Movement transaction, @PathVariable String id) {
        return movementService.update(transaction, id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return movementService.delete(id);
    }
}
