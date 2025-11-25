package com.breadhardit.travelagencykata.infrastructure.rest;

import com.breadhardit.travelagencykata.application.command.command.CreateCustomerCommand;
import com.breadhardit.travelagencykata.application.command.query.GetCustomerQuery;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.fecade.CustomerFacade;
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

    private final CustomerFacade facade;

    @PutMapping("/customers")
    @Transactional
    public ResponseEntity putCustomer(@RequestBody PutCustomerDTO customer) {
        log.info("POST customer {}", customer);
        return ResponseEntity.created(facade.createCustomer(customer)).build();
    }

    @GetMapping("/customers/{customer-id}")
    public ResponseEntity getCustomer(@PathVariable String customerId) {
        log.info("Getting the customer {}", customerId);

        return facade.getCustomerById(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/customers")
    public ResponseEntity getCustomers(@RequestParam(name = "passport-number") String passportNumber) {
        log.info("Getting the customer with the passport {}", passportNumber);

        return facade.getCustomerByPassport(passportNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
