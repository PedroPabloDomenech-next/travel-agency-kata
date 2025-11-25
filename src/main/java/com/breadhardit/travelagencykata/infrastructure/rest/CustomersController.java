package com.breadhardit.travelagencykata.infrastructure.rest;

import com.breadhardit.travelagencykata.application.command.CreateCustomerCommand;
import com.breadhardit.travelagencykata.application.command.query.GetCustomerQuery;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.GetCustomerDTO;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.PutCustomerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomersController {

    final CustomersRepository customersRepository;

    @PutMapping("/customers")
    @Transactional

    public ResponseEntity<PutCustomerDTO> putCustomer(@RequestBody PutCustomerDTO customer) {
        log.info("POST customer {}", customer);
        CreateCustomerCommand command = CreateCustomerCommand.builder()
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .customersRepository(customersRepository)
                .build();
        String id = command.handle();
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{customer-id}")
                        .buildAndExpand(id)
                        .toUri()).build();
    }

    @GetMapping("/customers/{customer-id}")
    public ResponseEntity<GetCustomerDTO> getCustomer(@PathVariable String customerId) {
        log.info("Getting the customer {}", customerId);
        Optional<Customer> customer = GetCustomerQuery.builder()
                .customersRepository(customersRepository)
                .id(customerId)
                .build().handle();
        return customer.map(value -> ResponseEntity.ok(toDto(value)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
    @GetMapping("/customers")
    public ResponseEntity<GetCustomerDTO> getCustomers(@RequestParam(name = "passport-number") String passportNumber) {
        log.info("Getting the customer with the passport {}",passportNumber);
        Optional<Customer> customer = GetCustomerQuery.builder()
                .customersRepository(customersRepository)
                .passport(passportNumber)
                .build().handle();
        return customer.map(value -> ResponseEntity.ok(toDto(value)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    private GetCustomerDTO toDto(Customer c) {
        return GetCustomerDTO.builder()
                .name(c.getName())
                .surnames(c.getSurnames())
                .birthDate(c.getBirthDate())
                .passportNumber(c.getPassportNumber())
                .build();
    }

}
