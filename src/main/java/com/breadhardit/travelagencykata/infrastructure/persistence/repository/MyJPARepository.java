package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import lombok.Builder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Data
@Scope("singleton")
@Repository
@Primary
@Transactional
public class MyJPARepository implements CustomersRepository {
    private final CustomersJPARepository customersJPARepository;

    @Override
    public void saveCustomer(Customer customer) {
        System.out.println("quiero guardar un customer");
        customersJPARepository.save(conversionCustomerToCustomerEntity(customer));
        System.out.println("puede que el customer se haya guardado");
               // te devuelve un customer, hay que transformarlo a customerEntity.
            // ojo porque en las clases DTO no están todos los atributos de customer así que hay que gestionarlos a mano
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerById(String id) {
        Optional<CustomerEntity> customerEntity = getCustomersJPARepository().findById(id);
        if (customerEntity.isEmpty()) {
            return Optional.empty();
        }
        Customer customer = conversionCustomerEntityToCustomer(customerEntity.get());
        System.out.println("acabo de pasar por getCustomerById");
        return customer == null ? Optional.empty() : Optional.of(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerByPassport(String passport) {  //hay que convertir este metodo para q use el metodo de customersJPARepository
        CustomerEntity customerEntity = getCustomersJPARepository().getByPassportNumber(passport);
        Customer customer = conversionCustomerEntityToCustomer(customerEntity);
        System.out.println("acabo de pasar por getCustomerByPassport");
        return customer == null ? Optional.empty() : Optional.of(customer);
    }

    private CustomerEntity conversionCustomerToCustomerEntity(Customer customer){
        if  (customer == null){
            return null;
        } else {
            return CustomerEntity.builder().
                    id(customer.getId()).
                    name(customer.getName()).
                    surnames(customer.getSurnames()).
                    birthDate(customer.getBirthDate()).
                    passportNumber(customer.getPassportNumber()).
                    enrollmentDate((customer.getEnrollmentDate() == null) ? LocalDate.now() : customer.getEnrollmentDate()).
                    active(customer.getActive() == null || customer.getActive()).
                    build();
        }
    }

    private Customer conversionCustomerEntityToCustomer(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            return null;
        } else {
            return Customer.builder().
                    id(customerEntity.getId()).
                    name(customerEntity.getName()).
                    surnames(customerEntity.getSurnames()).
                    birthDate(customerEntity.getBirthDate()).
                    passportNumber(customerEntity.getPassportNumber()).
                    enrollmentDate(customerEntity.getEnrollmentDate()).
                    active(customerEntity.getActive()).
                    build();
        }
    }
}
