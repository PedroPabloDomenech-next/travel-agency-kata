package com.breadhardit.travelagencykata.infrastructure.rest;

import com.breadhardit.travelagencykata.application.command.command.CreateCustomerCommand;
import com.breadhardit.travelagencykata.application.command.query.GetCustomerQuery;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.PutCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomersRepository customersRepository;

    public String createCustomer(PutCustomerDTO customerDTO) {
        return CreateCustomerCommand.builder()
                .name(customerDTO.getName())
                .surnames(customerDTO.getSurnames())
                .birthDate(customerDTO.getBirthDate())
                .passportNumber(customerDTO.getPassportNumber())
                .customersRepository(customersRepository)
                .build()
                .handle();
    }

    public Optional<Customer> getCustomerById(String id) {
        return GetCustomerQuery.builder()
                .customersRepository(customersRepository)
                .id(id)
                .build()
                .handle();
    }

    public Optional<Customer> getCustomerByPassport(String passport) {
        return GetCustomerQuery.builder()
                .customersRepository(customersRepository)
                .passport(passport)
                .build()
                .handle();
    }

}
