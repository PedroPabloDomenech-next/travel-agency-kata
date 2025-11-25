package com.breadhardit.travelagencykata.infrastructure.rest;

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

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomersController {

    private final CustomerService customerService;

    @PutMapping("/customers")
    @Transactional
    public ResponseEntity putCustomer(@RequestBody PutCustomerDTO customer) {
        log.info("POST customer {}", customer);
        String id = customerService.createCustomer(customer);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{customer-id}")
                        .buildAndExpand(id)
                        .toUri()).build();

    }

    @GetMapping("/customers/{customer-id}")
    public ResponseEntity getCustomer(@PathVariable String customerId) {
        log.info("Getting the customer {}", customerId);
        return customerService.getCustomerById(customerId)
                .map(this::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());

    }
    @GetMapping("/customers")
    public ResponseEntity getCustomers(@RequestParam(name = "passport-number") String passportNumber) {
        log.info("Getting the customer with the passport {}",passportNumber);
        return customerService.getCustomerByPassport(passportNumber)
                .map(this::toDTO)
                .map(dto -> ResponseEntity.ok(List.of(dto)))
                .orElse(ResponseEntity.noContent().build());

    }

    private GetCustomerDTO toDTO(Customer customer) {
        return GetCustomerDTO.builder()
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .build();
    }


}
