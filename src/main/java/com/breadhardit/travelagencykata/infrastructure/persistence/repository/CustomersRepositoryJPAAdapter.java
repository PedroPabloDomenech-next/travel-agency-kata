package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.mapper.CustomerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
@Profile("jpa")
@RequiredArgsConstructor
public class CustomersRepositoryJPAAdapter implements CustomersRepository {

    private final CustomersJPARepository customersJPARepository;

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity entity = CustomerEntityMapper.toEntity(customer);
        customersJPARepository.save(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return customersJPARepository.findById(id)
                .map(CustomerEntityMapper::toDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportNumber) {
        CustomerEntity entity = customersJPARepository.findByPassportNumber(passportNumber);
        return Optional.ofNullable(CustomerEntityMapper.toDomain(entity));
    }
}
