package com.breadhardit.travelagencykata.infrastructure.persistence.mapper;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;

import java.time.LocalDate;

public class CustomerEntityMapper {

    // Convierte de domain a entity
    public static CustomerEntity toEntity(Customer customer) {
        if (customer == null) return null;

        return CustomerEntity.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                // Si enrollmentDate es null, se pone la fecha actual
                .enrollmentDate(customer.getEnrollmentDate() != null ? customer.getEnrollmentDate() : LocalDate.now())
                // Si active es null, se pone true
                .active(customer.getActive() != null ? customer.getActive() : true)
                .build();
    }

    // Convierte de entity a domain
    public static Customer toDomain(CustomerEntity entity) {
        if (entity == null) return null;

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
