package com.breadhardit.travelagencykata.infrastructure.persistence.entity;

import com.breadhardit.travelagencykata.domain.Customer;

import java.time.LocalDate;

public class CustomerMapper {

    public static Customer toCustomer(CustomerEntity customerEntity) {
        return Customer.builder()
                .id(customerEntity.getId())
                .name(customerEntity.getName())
                .surnames(customerEntity.getSurnames())
                .birthDate(customerEntity.getBirthDate())
                .passportNumber(customerEntity.getPassportNumber())
                .enrollmentDate(customerEntity.getEnrollmentDate())
                .active(customerEntity.getActive())
                .build();
    }

    public static CustomerEntity toCustomerEntity(Customer customer) {
       CustomerEntity customerEntity = CustomerEntity.builder()
               .id(customer.getId())
               .name(customer.getName())
               .surnames(customer.getSurnames())
               .birthDate(customer.getBirthDate())
               .passportNumber(customer.getPassportNumber())
               .enrollmentDate(customer.getEnrollmentDate())
               .active(customer.getActive()).build();

        if(customerEntity.getEnrollmentDate() == null){
            customerEntity.setEnrollmentDate(LocalDate.now());
        }
        if(customerEntity.getActive() == null){
            customerEntity.setActive(true);
        }
        return customerEntity;
    }

}
