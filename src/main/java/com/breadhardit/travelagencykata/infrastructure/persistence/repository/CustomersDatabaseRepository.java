package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomersDatabaseRepository implements CustomersRepository {

    private final CustomersJPARepository jpaRepository;

    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity entity = CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate(customer.getEnrollmentDate())
                .active(customer.getActive())
                .build();

        jpaRepository.save(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return jpaRepository.findById(id)
                .map(this::toDomainModel);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passport) {
        return jpaRepository.findByPassportNumber(passport)
                .map(this::toDomainModel);
    }

    private Customer toDomainModel(CustomerEntity entity) {
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
