package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CustomerEntity es una clase que modela la clase Customer del dominio

@Repository
public interface CustomersJPARepository extends JpaRepository<CustomerEntity,String> {
    CustomerEntity getByPassportNumber(String passportNumber);
}
