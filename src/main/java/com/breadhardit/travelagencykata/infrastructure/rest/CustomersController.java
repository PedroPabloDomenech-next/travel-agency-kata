package com.breadhardit.travelagencykata.infrastructure.rest;

import com.breadhardit.travelagencykata.application.command.command.CreateCustomerCommand;
import com.breadhardit.travelagencykata.application.command.query.GetCustomerQuery;
import com.breadhardit.travelagencykata.application.command.query.GetCustomerQueryHandler;
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

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomersController {

    // Cambiado de CustomersRepository a GetCustomerQueryHandler
    private final GetCustomerQueryHandler getCustomerQueryHandler;
    // Agregado el CreateCustomerCommandHandler (asumiendo refactorización similar)
    private final CreateCustomerCommand command;

    // ... (Método putCustomer, se asume que CreateCustomerCommand también se refactorizó a Handler)

    @GetMapping("/customers/{customer-id}")
    public ResponseEntity getCustomer(@PathVariable("customer-id") String customerId) { // Corregido: @PathVariable String customerId
        log.info("Getting the customer {}", customerId);
        Optional<Customer> customer = getCustomerQueryHandler.handle(
                GetCustomerQuery.builder()
                        .id(customerId)
                        .build()
        );
        return customer.isEmpty() ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(GetCustomerDTO.builder()
                // ... DTO mapping
                .build());
    }

    @GetMapping("/customers")
    public ResponseEntity getCustomers(@RequestParam(name = "passport-number") String passportNumber) {
        log.info("Getting the customer with the passport {}",passportNumber);
        Optional<Customer> customer = getCustomerQueryHandler.handle(
                GetCustomerQuery.builder()
                        .passport(passportNumber)
                        .build()
        );
        return customer.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(
                        List.of(GetCustomerDTO.builder()
                                // ... DTO mapping
                                .build())
                );
    }
}