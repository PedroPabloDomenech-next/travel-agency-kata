package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Scope("singleton")
@RequiredArgsConstructor
public class AdapterCustomerRepository implements CustomersRepository {
    private final CustomersJPARepository repository;


    @Override
    public void saveCustomer(Customer customer) {
        CustomerEntity customerEntity = CustomerEntity.builder().build();
        repository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Optional<CustomerEntity> customerEntity = repository.findById(id);
        return Optional.of(Customer.builder().build());
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        CustomerEntity customerEntity = repository.getByPassportNumber(id);
        return Optional.of(Customer.builder().build());
    }


}
