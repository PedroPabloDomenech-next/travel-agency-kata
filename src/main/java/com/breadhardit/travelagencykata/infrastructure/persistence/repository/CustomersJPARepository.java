package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * IMPORTANTE: LA IMPLEMENTACIÓN LA INYECTA SPRING EN TIEMPO DE EJECUCIÓN: NO SE NECESITA IMPLEMENTAR ESTA INTERFAZ
 * EXPLÍCITAMENTE
 */
@Repository
public interface CustomersJPARepository extends JpaRepository<CustomerEntity,String> {
    // getReferenceById viene dado por la interfaz

    Optional<CustomerEntity> getReferenceByPassportNumber(String passportNumber);
}
