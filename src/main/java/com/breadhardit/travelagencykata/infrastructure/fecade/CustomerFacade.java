package com.breadhardit.travelagencykata.infrastructure.fecade;


import com.breadhardit.travelagencykata.application.command.command.CreateCustomerCommand;
import com.breadhardit.travelagencykata.application.command.query.GetCustomerQuery;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.GetCustomerDTO;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.PutCustomerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerFacade {

    private final CustomersRepository customersRepository;


    public URI createCustomer(PutCustomerDTO customerDTO) {
        CreateCustomerCommand command = CreateCustomerCommand.builder()
                .name(customerDTO.getName())
                .surnames(customerDTO.getSurnames())
                .birthDate(customerDTO.getBirthDate())
                .passportNumber(customerDTO.getPassportNumber())
                .customersRepository(customersRepository)
                .build();

        String id = command.handle();

        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{customer-id}")
                .buildAndExpand(id)
                .toUri();
    }


    public Optional<GetCustomerDTO> getCustomerById(String id) {
        Optional<Customer> customer = GetCustomerQuery.builder()
                .id(id)
                .customersRepository(customersRepository)
                .build()
                .handle();

        return customer.map(c -> GetCustomerDTO.builder()
                .name(c.getName())
                .surnames(c.getSurnames())
                .birthDate(c.getBirthDate())
                .passportNumber(c.getPassportNumber())
                .build());
    }


    public Optional<List<GetCustomerDTO>> getCustomerByPassport(String passportNumber) {
        Optional<Customer> customer = GetCustomerQuery.builder()
                .passport(passportNumber)
                .customersRepository(customersRepository)
                .build()
                .handle();

        return customer.map(c ->
                List.of(GetCustomerDTO.builder()
                        .name(c.getName())
                        .surnames(c.getSurnames())
                        .birthDate(c.getBirthDate())
                        .passportNumber(c.getPassportNumber())
                        .build())
        );
    }
}