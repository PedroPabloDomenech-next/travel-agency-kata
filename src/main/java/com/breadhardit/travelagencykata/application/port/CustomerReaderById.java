package com.breadhardit.travelagencykata.application.port;

import com.breadhardit.travelagencykata.domain.Customer;

import java.util.Optional;

public interface CustomerReaderById {
    Optional<Customer> getCustomerById(String id);
}
