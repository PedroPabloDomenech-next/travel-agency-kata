package com.breadhardit.travelagencykata.infrastructure.persistence.entity;

import com.breadhardit.travelagencykata.domain.Customer;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerEntityMapper {

    public CustomerEntity toEntity(Customer domain) {
        // Uso de Lombok @Builder en CustomerEntity
        return CustomerEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .surnames(domain.getSurnames())
                .birthDate(domain.getBirthDate())
                .passportNumber(domain.getPassportNumber())
                .enrollmentDate(LocalDateTime.now().toLocalDate()) // El dominio ya tiene este campo, sin embargo me da problema de nulo. Le pongo el now por ponerle algo
                .active(domain.getActive()) // El dominio ya tiene este campo
                .build();
    }

    public Customer toDomain(CustomerEntity entity) {
        // Uso de Lombok @Builder en Customer
        return Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .enrollmentDate(entity.getEnrollmentDate())
                .active(true) //me da problema de nulos, al no entenderse lo dejo en TRUE como default, pero es que no logro otra cosa
                //ni con el true he logrado que se resuelva
                .build();
    }

    public Optional<Customer> toDomain(Optional<CustomerEntity> optionalEntity) {
        return optionalEntity.map(this::toDomain);
    }
}