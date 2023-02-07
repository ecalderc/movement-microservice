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
        Flux<Movement> movementsFlux = movementRepository
                .findAll()
                .filter(x -> x.getAccountNumber().equals(accountNumber) && x.getCommission() > 0);
        return movementsFlux;
    }

    public Mono<Movement> saveMovement(Movement dataMovement) {
        dataMovement.setStatus("active");
        return movementRepository.save(dataMovement);

    }

    @Override
    public Mono<Movement> updateMovement(Movement dataMovement) {

        Mono<Movement> transactionMono = findByMovementNumber(dataMovement.getMovementNumber());
        try {
            dataMovement.setDni(transactionMono.block().getDni());
            dataMovement.setAmount(transactionMono.block().getAmount());
            dataMovement.setAccountNumber(transactionMono.block().getAccountNumber());
            dataMovement.setMovementNumber(transactionMono.block().getMovementNumber());
            dataMovement.setTypeTransaction(transactionMono.block().getTypeTransaction());
            dataMovement.setStatus(transactionMono.block().getStatus());
            dataMovement.setCreationDate(transactionMono.block().getCreationDate());
            return movementRepository.save(dataMovement);
        } catch (Exception e) {
            return Mono.<Movement>error(new Error("The movement " + dataMovement.getMovementNumber() + " do not exists"));
        }
    }

    @Override
    public Mono<Movement> deleteMovement(String number) {
        log.info("Deleting movement by number: " + number);
        return findByMovementNumber(number)
                .flatMap(movement -> movementRepository.delete(movement).then(Mono.just(movement)));
    }

}
