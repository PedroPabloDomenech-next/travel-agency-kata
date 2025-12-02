package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
@Repository
@AllArgsConstructor
@Primary
public class CustomersDatabaseRepository implements CustomersRepository {

    private final CustomersJPARepository customersJPARepository;

    @Override
    public void saveCustomer(Customer customer) {
        System.out.println(customer);
        this.customersJPARepository.save(toEntity(customer));
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return customersJPARepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportNumber) {
        return customersJPARepository.findByPassportNumber(passportNumber).map(this::toDomain);
    }

    private CustomerEntity toEntity(Customer c) {
        return CustomerEntity.builder()
                .id(c.getId())
                .name(c.getName())
                .surnames(c.getSurnames())
                .birthDate(c.getBirthDate())
                .passportNumber(c.getPassportNumber())
                .enrollmentDate(LocalDate.now())
                .active(true)
                .build();
    }
    private Customer toDomain(CustomerEntity e) {
        return Customer.builder()
                .id(e.getId())
                .name(e.getName())
                .surnames(e.getSurnames())
                .birthDate(e.getBirthDate())
                .passportNumber(e.getPassportNumber())
                .enrollmentDate(e.getEnrollmentDate())
                .active(e.getActive())
                .build();
    }
}
