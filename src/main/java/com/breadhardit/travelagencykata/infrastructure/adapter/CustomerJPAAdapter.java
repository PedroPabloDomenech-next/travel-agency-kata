package com.breadhardit.travelagencykata.infrastructure.adapter;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.mapper.CustomerMapper;
import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class CustomerJPAAdapter implements CustomersRepository{
    private final CustomersJPARepository jpaRepository;
    private final CustomerMapper mapper;


    public CustomerJPAAdapter(CustomersJPARepository jpaRepository,
                              CustomerMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }
    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity entity = mapper.toEntity(customer);

        jpaRepository.save(entity);

    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return jpaRepository.findById(id)
                .map(entity -> mapper.toDomain(entity));
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportNumber) {
        return jpaRepository.getCustomerByPassportNumber(passportNumber)
                .map(entity -> mapper.toDomain(entity));
    }
}
