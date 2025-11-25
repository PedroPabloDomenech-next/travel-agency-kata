package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Scope("singleton")
@Data
public class CustomersRespositoryAdapter implements CustomersRepository {
    private final CustomersJPARepository repository;
    @Override
    public void saveCustomer(Customer customer) {
        System.out.println("Debugging " + customer.toString());
        CustomerEntity customerEntity = CustomerEntity.builder().id(customer.getId()).name(customer.getName())
                .surnames(customer.getSurnames()).birthDate(customer.getBirthDate()).passportNumber(customer.getPassportNumber())
                .enrollmentDate(customer.getEnrollmentDate()).active(customer.getActive()).build();
        repository.save(customerEntity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        System.out.println("Debugging id :" + id);
        Optional<CustomerEntity> entityOpt = repository.findById(id);
        if (entityOpt.isEmpty()) {
            return Optional.empty();
        }
        CustomerEntity entity = entityOpt.get();
        return Optional.of(Customer.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .enrollmentDate(entity.getEnrollmentDate())
                .active(entity.getActive())
                .build());
    }


    @Override
    public Optional<Customer> getCustomerByPassport(String id) {
        System.out.println("Passport id :" + id);
        if(repository.findByPassportNumber(id) == null){
            return Optional.empty();
        }
        CustomerEntity entity = repository.findByPassportNumber(id);
        return Optional.ofNullable(Customer.builder().id(entity.getId()).name(entity.getName()).surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate()).passportNumber(entity.getPassportNumber()).enrollmentDate(entity.getEnrollmentDate())
                .active(entity.getActive()).build());
    }
}
