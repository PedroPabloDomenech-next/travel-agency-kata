package com.breadhardit.travelagencykata.application.command.query;

import com.breadhardit.travelagencykata.domain.Customer;
import com.breadhardit.travelagencykata.application.port.CustomersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetCustomerQueryHandler {

    private final CustomersRepository customersRepository; // Inyectado por constructor

    public Optional<Customer> handle(GetCustomerQuery query) {
        if (StringUtils.hasText(query.getPassport())) {
            return customersRepository.getCustomerByPassport(query.getPassport());
        }
        if (StringUtils.hasText(query.getId())) {
            return customersRepository.getCustomerById(query.getId());
        }
        return Optional.empty();
    }
}
