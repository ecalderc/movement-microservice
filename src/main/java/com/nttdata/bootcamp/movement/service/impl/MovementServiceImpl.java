package com.nttdata.bootcamp.movement.service.impl;

import com.nttdata.bootcamp.movement.entity.Movement;
import com.nttdata.bootcamp.movement.repository.MovementRepository;
import com.nttdata.bootcamp.movement.service.MovementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;


@Slf4j
@AllArgsConstructor
@Service
public class MovementServiceImpl implements MovementService {
    @Autowired
    private MovementRepository movementRepository;

    @Override
    public Flux<Movement> findAll() {
        log.info("Searching for all movements");
        return movementRepository.findAll();
    }

    @Override
    public Flux<Movement> findByAccountNumber(String accountNumber) {
        log.info("Find for movement with account number: " + accountNumber);
        return movementRepository.findAll()
                .filter(x -> x.getAccountNumber().equals(accountNumber));
    }

    @Override
    public Mono<Movement> findByMovementNumber(String movementNumber) {
        log.info("Find for movement with movement number: " + movementNumber);
        return movementRepository.findAll()
                .filter(x -> x.getMovementNumber().equals(movementNumber))
                .next();
    }

    @Override
    public Flux<Movement> findCommissionByAccountNumber(String accountNumber) {
        return movementRepository.findAll()
                .filter(x -> x.getAccountNumber().equals(accountNumber) && x.getCommission() > 0);
    }

    public Mono<Movement> saveMovement(Movement dataMovement) {
        log.info("Saving movement");

        Movement m = new Movement();
        m.setStatus("active");
        m.setCreationDate(new Date());
        m.setModificationDate(new Date());
        m.setTypeTransaction("Transfer");
        return movementRepository.save(m);
    }

    @Override
    public Mono<Movement> updateMovements(Movement dataMovement) {
        return findByMovementNumber(dataMovement.getMovementNumber())
                .flatMap(updMov -> {
                    log.info("Updating number of movements");

                    updMov.setMovementNumber(dataMovement.getMovementNumber());
                    updMov.setModificationDate(new Date());
                    return movementRepository.save(updMov);
                });
    }

    @Override
    public Mono<Movement> updateCommission(Movement dataMovement) {
        return findByMovementNumber(dataMovement.getMovementNumber())
                .flatMap(updMov -> {
                    log.info("Updating commission");

                    updMov.setMovementNumber(dataMovement.getCommission().toString());
                    updMov.setModificationDate(new Date());
                    return movementRepository.save(updMov);
                });
    }

    @Override
    public Mono<Movement> deleteMovement(String number) {
        log.info("Deleting movement by number: " + number);
        return findByMovementNumber(number)
                .flatMap(movement -> movementRepository.delete(movement).then(Mono.just(movement)));
    }

}
