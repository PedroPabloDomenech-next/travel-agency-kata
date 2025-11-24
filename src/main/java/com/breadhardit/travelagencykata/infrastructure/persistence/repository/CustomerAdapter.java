package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class CustomerAdapter implements CustomersRepository {

    private final CustomersJPARepository jpaCustomerRepository;

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity entity = toEntity(customer);
        jpaCustomerRepository.save(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return jpaCustomerRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passport) {
        return jpaCustomerRepository.findByPassportNumber(passport)
                .map(this::toDomain);
    }

    private CustomerEntity toEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(customer.getEnrollmentDate())
                .active(customer.getActive())
                .build();
    }

    private Customer toDomain(CustomerEntity entity) {
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .enrollmentDate(entity.getEnrollmentDate())
                .active(entity.getActive())
                .build();
    }
}
