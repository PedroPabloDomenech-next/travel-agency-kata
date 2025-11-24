package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersJPARepository extends JpaRepository<CustomerEntity, String> {

    /**
     * Busca un cliente por su número de pasaporte.
     * Spring Data JPA genera automáticamente la query a partir del nombre del método.
     */
    Optional<CustomerEntity> findByPassportNumber(String passportNumber);
}
