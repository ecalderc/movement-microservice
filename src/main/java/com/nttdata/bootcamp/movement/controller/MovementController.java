package com.nttdata.bootcamp.movement.controller;

import com.nttdata.bootcamp.movement.entity.dto.BusinessCustomerDto;
import com.nttdata.bootcamp.movement.entity.dto.PersonalCustomerDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import com.nttdata.bootcamp.movement.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nttdata.bootcamp.movement.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/customer")
public class MovementController {

    @Autowired
    private MovementService movementService;

    //Customer search
    @GetMapping("/findAll")
    public Flux<Customer> findAllCustomers() {
        return movementService.findAllCustomers();
    }

    //Search for customers by DNI
    @GetMapping("/findByDni/{dni}")
    public Mono<Customer> findCustomerByDni(@PathVariable("dni") String dni) {
        return movementService.findCustomerByDni(dni);
    }

    //Save personal customer
    @PostMapping(value = "/savePersonalCustomer")
    public Mono<Customer> savePersonalCustomer(@Valid @RequestBody PersonalCustomerDto customer) {
        return movementService.savePersonalCustomer(customer);
    }

    //Save business customer
    @PostMapping(value = "/saveBusinessCustomer")
    public Mono<Customer> saveBusinessCustomer(@Valid @RequestBody BusinessCustomerDto customer) {
        return movementService.saveBusinessCustomer(customer);
    }

    //Update customer status
    @PutMapping("/updateStatus/{dni}")
    public Mono<Customer> updateCustomerStatus(@PathVariable("dni") String dni, @RequestBody Customer customer) {
        return movementService.updateCustomerStatus(customer);
    }

    //Update customer address
    @PutMapping("/updateAddress/{dni}")
    public Mono<Customer> updateCustomerAddress(@PathVariable("dni") String dni, @RequestBody Customer customer) {
        return movementService.updateCustomerAddress(customer);
    }

    //Delete customer
    @DeleteMapping("/delete/{dni}")
    public Mono<Customer> deleteCustomer(@PathVariable("dni") String dni) {
        return movementService.deleteCustomer(dni);
    }

}
