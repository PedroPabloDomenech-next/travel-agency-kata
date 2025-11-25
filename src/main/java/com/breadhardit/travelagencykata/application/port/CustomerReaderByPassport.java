package com.breadhardit.travelagencykata.application.port;

import com.breadhardit.travelagencykata.domain.Customer;

import java.util.Optional;

public interface CustomerReaderByPassport {
    Optional<Customer> getCustomerByPassport(String passportNumber);
}
