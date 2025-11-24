package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

// Copio las mismas etiquetas que tenía CustomersInMemoryRepository.java
@Repository

// Los Beans son singleton por defecto: me ahorro la etiqueta

// Necesaria cuando hay varias clases implementando el puerto, y por tanto candidatas a usarse en CustomerController:
@Primary

public class CustomersInDBRepository implements CustomersRepository {

    CustomersJPARepository customersJPARepository;

    // Constructor con inyección, mejor que @Autowired según SonarQube
    public CustomersInDBRepository(CustomersJPARepository customersJPARepository) {
        this.customersJPARepository = customersJPARepository;
    }


    @Override
    public void saveCustomer(Customer customer) {
        this.customersJPARepository.save(toEntity(customer));
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        return this.customersJPARepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passport) {
        return this.customersJPARepository.getReferenceByPassportNumber(passport).map(this::toDomain);
    }

    public CustomerEntity toEntity(Customer customer){
        return CustomerEntity.builder().id(customer.getId())
                .name(customer.getName())
                .surnames(customer.getSurnames())
                .birthDate(customer.getBirthDate())
                .passportNumber(customer.getPassportNumber())
                .enrollmentDate((customer.getEnrollmentDate() == null) ? LocalDate.now() : customer.getEnrollmentDate())
                // Más simplificado que con un condicional ternario
                .active(customer.getActive() == null || customer.getActive())
                .build();
    }

    public Customer toDomain(CustomerEntity entity){
        return Customer.builder().id(entity.getId())
                .name(entity.getName())
                .surnames(entity.getSurnames())
                .birthDate(entity.getBirthDate())
                .passportNumber(entity.getPassportNumber())
                .enrollmentDate(entity.getEnrollmentDate())
                .active(entity.getActive())
                .build();
    }

}
