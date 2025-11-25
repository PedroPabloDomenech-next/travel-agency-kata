package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.Builder;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Scope("singleton")

public class RepositoryAdapter implements CustomersRepository {
    private CustomersJPARepository databaseRepository;
    private CustomerTransformer transformer;

    @Override
    public void saveCustomer(Customer customer) {
        databaseRepository.save(transformer.toEntity(customer));
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        CustomerEntity customerEntity = databaseRepository.getReferenceById(id);
        Customer customer = transformer.toDomain(customerEntity);
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        CustomerEntity customerEntity = databaseRepository.getByPassportNumber(id);
        Customer customer = transformer.toDomain(customerEntity);
        return Optional.ofNullable(customer);
    }
}
