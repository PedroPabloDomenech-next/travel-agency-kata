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

    @Override
    public void saveCustomer(Customer customer) {
        databaseRepository.save(transform(customer));
    }

    private Customer transform(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .enrollmentDate((entity.getEnrollmentDate() == null) ? LocalDate.now() : entity.getEnrollmentDate())
                .active(entity.getActive() == null || entity.getActive())
                .build();
    }

    private CustomerEntity transform(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate((customer.getEnrollmentDate() == null) ? LocalDate.now() : customer.getEnrollmentDate())
                .active(customer.getActive() == null || customer.getActive())
                .build();
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        CustomerEntity customerEntity = databaseRepository.getReferenceById(id);
        Customer customer = transform(customerEntity);
        return Optional.ofNullable(customer);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        CustomerEntity customerEntity = databaseRepository.getByPassportNumber(id);
        Customer customer = transform(customerEntity);
        return Optional.ofNullable(customer);
    }
}
