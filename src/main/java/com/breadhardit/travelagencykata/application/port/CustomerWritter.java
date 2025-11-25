package com.breadhardit.travelagencykata.application.port;

import com.breadhardit.travelagencykata.domain.Customer;

public interface CustomerWritter {
    void saveCustomer(Customer customer);
}
