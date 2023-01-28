package com.nttdata.bootcamp.movement.service.impl;

import com.nttdata.bootcamp.movement.entity.Movement;
import com.nttdata.bootcamp.movement.entity.dto.CurrentAccountDto;
import com.nttdata.bootcamp.movement.entity.dto.FixedTermDto;
import com.nttdata.bootcamp.movement.entity.dto.SavingAccountDto;
import com.nttdata.bootcamp.movement.repository.MovementRepository;
import com.nttdata.bootcamp.movement.service.MovementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@AllArgsConstructor
@Service
public class MovementServiceImpl implements MovementService {
    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private WebClient webClient;

    //public MovementServiceImpl(MovementRepository movementRepository, WebClient.Builder webClient,@Value("${passive}") String passiveUrl) {
    public MovementServiceImpl(MovementRepository movementRepository, WebClient.Builder webClient, String passiveUrl) {
        this.movementRepository = movementRepository;
        this.webClient = webClient.baseUrl(passiveUrl).build();
    }

    private Mono<CurrentAccountDto> findCurrentAccountByDni(String dni, String account) {
        return webClient.get().uri("/currentAccount/dni/" + dni + "/account/" + account)
                .retrieve().bodyToMono(CurrentAccountDto.class);
    }

    private Mono<SavingAccountDto> findSavingAccountByDni(String dni, String account) {
        return webClient.get().uri("/savingAccount/dni/" + dni + "/account/" + account)
                .retrieve().bodyToMono(SavingAccountDto.class);
    }

    private Mono<FixedTermDto> findFixedTermAccountByDni(String dni, String account) {
        return webClient.get().uri("/fixedTermAccount/dni/" + dni + "/account/" + account)
                .retrieve().bodyToMono(FixedTermDto.class);
    }

    private Mono<CurrentAccountDto> updateCurrentAccount(CurrentAccountDto currentAccount) {
        return webClient.put().uri("/currentAccount/" + currentAccount.getId())
                .body(Mono.just(currentAccount), CurrentAccountDto.class)
                .retrieve()
                .bodyToMono(CurrentAccountDto.class);
    }

    private Mono<SavingAccountDto> updateSavingAccount(SavingAccountDto savingAccount) {
        return webClient.put().uri("/savingAccount/" + savingAccount.getId())
                .body(Mono.just(savingAccount), SavingAccountDto.class)
                .retrieve()
                .bodyToMono(SavingAccountDto.class);
    }

    private Mono<FixedTermDto> updateFixedTermAccount(FixedTermDto fixedTermAccount) {
        return webClient.put().uri("/fixedTermAccount/" + fixedTermAccount.getId())
                .body(Mono.just(fixedTermAccount), FixedTermDto.class)
                .retrieve()
                .bodyToMono(FixedTermDto.class);
    }

    @Override
    public Flux<Movement> findAll() {
        return movementRepository.findAll();
    }

    @Override
    public Mono<Movement> saveTransactionOfCurrentAccount(Movement transaction) {
        Mono<CurrentAccountDto> account = findCurrentAccountByDni(transaction.getDni(), transaction.getAccountNumber()).flatMap(x -> {
            x.setBalance(x.getBalance().add(transaction.getAmount().negate()));
            return updateCurrentAccount(x);
        });
        return account.then(movementRepository.save(transaction));
    }

    @Override
    public Mono<Movement> saveTransactionOfSavingAccount(Movement transaction) {
        Mono<SavingAccountDto> account = findSavingAccountByDni(transaction.getDni(), transaction.getAccountNumber()).flatMap(x -> {
            x.setBalance(x.getBalance().add(transaction.getAmount().negate()));
            return updateSavingAccount(x);
        });
        return account.then(movementRepository.save(transaction));
    }

    @Override
    public Mono<Movement> saveTransactionOfFixedTermAccount(Movement transaction) {
        Mono<FixedTermDto> account = findFixedTermAccountByDni(transaction.getDni(), transaction.getAccountNumber()).flatMap(x -> {
            x.setBalance(x.getBalance().add(transaction.getAmount().negate()));
            return updateFixedTermAccount(x);
        });
        return account.then(movementRepository.save(transaction));
    }

    @Override
    public Mono<Movement> findById(String id) {
        return movementRepository.findById(id);
    }

    @Override
    public Mono<Movement> update(Movement transaction, String id) {
        return movementRepository.findById(id).flatMap(x -> {
            x.setAmount(transaction.getAmount());
            x.setCurrency(transaction.getCurrency());
            x.setAccountNumber(transaction.getAccountNumber());
            x.setCvc(transaction.getCvc());
            return movementRepository.save(x);
        });
    }

    @Override
    public Mono<Void> delete(String id) {
        return movementRepository.deleteById(id);
    }

}
