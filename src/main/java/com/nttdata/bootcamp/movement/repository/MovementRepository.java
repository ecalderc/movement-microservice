package com.nttdata.bootcamp.movement.repository;

import com.nttdata.bootcamp.movement.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

//Mongodb Repository
@Repository
public interface MovementRepository extends ReactiveMongoRepository<Customer, String> {
}
