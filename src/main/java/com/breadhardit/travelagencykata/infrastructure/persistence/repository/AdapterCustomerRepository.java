package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@Scope("singleton")
@RequiredArgsConstructor
public class AdapterCustomerRepository implements CustomersRepository {
    private final CustomersJPARepository repository;


    @Override
    public void saveCustomer(Customer customer) {
        repository.save(CustomerMapper.toCustomerEntity(customer));
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        Optional<CustomerEntity> customerEntity = repository.findById(id);
        if(customerEntity.isPresent()){
            CustomerEntity customerEntity1 = customerEntity.get();
            return Optional.of(CustomerMapper.toCustomer(customerEntity1));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        CustomerEntity customerEntity = repository.getByPassportNumber(id);
        if(customerEntity != null){
            return Optional.of(CustomerMapper.toCustomer(customerEntity));
        }
        return Optional.empty();
    }


}
