package com.nttdata.bootcamp.movement.service;

import com.nttdata.bootcamp.movement.entity.Customer;
import com.nttdata.bootcamp.movement.entity.dto.BusinessCustomerDto;
import com.nttdata.bootcamp.movement.entity.dto.PersonalCustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//Interface Service
public interface MovementService {

    Flux<Customer> findAllCustomers();

    Mono<Customer> findCustomerByDni(String dni);

    Mono<Customer> savePersonalCustomer(PersonalCustomerDto dataCustomer);

    Mono<Customer> saveBusinessCustomer(BusinessCustomerDto dataCustomer);

    Mono<Customer> updateCustomerStatus(Customer dataCustomer);

    Mono<Customer> updateCustomerAddress(Customer dataCustomer);

    Mono<Customer> deleteCustomer(String dni);

}
