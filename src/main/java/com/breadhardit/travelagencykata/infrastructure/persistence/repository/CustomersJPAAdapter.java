package com.breadhardit.travelagencykata.infrastructure.persistence.repository;

import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntity;
import com.breadhardit.travelagencykata.infrastructure.persistence.entity.CustomerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary // Para que Spring inyecte este en lugar del InMemory, si sigue existiendo
@RequiredArgsConstructor
public class CustomersJPAAdapter implements CustomersRepository {

    private final CustomersJPARepository jpaRepository;
    private final CustomerEntityMapper mapper;

    @Override
    public void saveCustomer(Customer customer) {
        // 1. Mapear Dominio a Entidad de Persistencia
        CustomerEntity entity = mapper.toEntity(customer);
        // 2. Guardar la Entidad
        jpaRepository.saveAndFlush(entity);
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        // 1. Obtener Entidad de Persistencia (Optional<CustomerEntity>)
        Optional<CustomerEntity> entity = jpaRepository.findById(id);
        // 2. Mapear a Dominio (Optional<Customer>)
        return mapper.toDomain(entity);
    }

    @Override
    public Optional<Customer> getCustomerByPassport(String passportNumber) {
        // 1. Obtener Entidad de Persistencia
        CustomerEntity entity = jpaRepository.getByPassportNumber(passportNumber);
        // 2. Mapear a Dominio (requiere manejo de Optional)
        return Optional.ofNullable(entity)
                .map(mapper::toDomain);
    }
}