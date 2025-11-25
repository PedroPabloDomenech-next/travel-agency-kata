package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;

import java.time.LocalDate;

public class CustomerTransformer {
    public Customer toDomain(CustomerEntity entity) {
        if (entity==null) return null;
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

    public CustomerEntity toEntity(Customer customer) {
        if (customer==null) return null;
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
}
