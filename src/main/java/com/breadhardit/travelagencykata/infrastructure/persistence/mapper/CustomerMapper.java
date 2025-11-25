package com.breadhardit.travelagencykata.infrastructure.persistence.mapper;


import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(Customer domain) {
        if (domain == null) return null;

        return CustomerEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .surnames(domain.getSurnames())
                .birthDate(domain.getBirthDate())
                .passportNumber(domain.getPassportNumber())
                .enrollmentDate(domain.getEnrollmentDate())
                .active(domain.getActive())
                .build();
    }

    public Customer toDomain(CustomerEntity entity) {
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
