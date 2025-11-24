package com.breadhardit.travelagencykata.infrastructure.persistence.entity;
import com.breadhardit.travelagencykata.domain.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "CUSTOMERS")
@Data
@Builder
public class CustomerEntity {
    @Id
    @Column(name = "ID")
    String id;
    @Column(name = "NAME")
    String name;
    @Column(name = "SURNAMES")
    String surnames;
    @Column(name = "BIRTH_DATE")
    LocalDate birthDate;
    @Column(name = "PASSPORT_NUMBER")
    String passportNumber;
    @Column(name = "ENROLLMENT_DATE")
    LocalDate enrollmentDate;
    @Column(name = "ACTIVE")
    Boolean active;
    public static CustomerEntity fromDomain(Customer customer) {
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

    public Customer toDomain() {
        return Customer.builder()
                .id(this.id)
                .name(this.name)
                .surnames(this.surnames)
                .birthDate(this.birthDate)
                .passportNumber(this.passportNumber)
                .enrollmentDate(this.enrollmentDate)
                .active(this.active)
                .build();
    }
}