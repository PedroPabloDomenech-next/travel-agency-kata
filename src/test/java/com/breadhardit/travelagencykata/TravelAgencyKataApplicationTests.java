package com.breadhardit.travelagencykata;

import com.breadhardit.travelagencykata.infrastructure.rest.CustomersController;
import com.breadhardit.travelagencykata.infrastructure.rest.dto.PutCustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootTest
@Slf4j
@DirtiesContext
class TravelAgencyKataApplicationTests {
    @Autowired
    CustomersController customersController;

    @BeforeEach
    public void beforeEach() {}

    @Test
    void contextLoads() {
        log.info("Context Loaded");
    }

    // HELPERS

    private ResponseEntity<?> createCustomer() {
        return customersController.putCustomer(
                PutCustomerDTO.builder()
                        .name("Pepe")
                        .surnames("Perez")
                        .birthDate(LocalDate.now())
                        .passportNumber("123")
                        .build()
        );
    }

    // Obtain the id from the location
    private String extractId(ResponseEntity<?> response) {
        String location = Objects.requireNonNull(response.getHeaders().getLocation()).getPath();
        return location.substring(location.lastIndexOf("/") + 1);
    }

    // TESTS SEPARADOS

    @Test
    // Assert the response is OK
    void givenAUserWhenCreatedThenStatusCreated() {
        var putCustomerResponse = createCustomer();
        Assertions.assertEquals(HttpStatus.CREATED, putCustomerResponse.getStatusCode());
    }

    @Test
    // Assert response has location header
    void givenAUserWhenCreatedThenLocationHeaderExists() {
        var putCustomerResponse = createCustomer();
        Assertions.assertTrue(putCustomerResponse.getHeaders().containsKey(HttpHeaders.LOCATION));
    }

    @Test
    // Call get method
    void givenAUserWhenCreatedThenGetByIdReturnsOK() {
        var putConsumerResponse = createCustomer();
        String id = extractId(putConsumerResponse);

        var getCustomerResponse = customersController.getCustomer(id);
        Assertions.assertEquals(HttpStatus.OK, getCustomerResponse.getStatusCode());
        Assertions.assertTrue(getCustomerResponse.hasBody());
    }

    @Test
    // Call get by passport method should return 200 with body
    void givenAUserWhenCreatedThenGetByPassportReturnsOK() {
        createCustomer(); // Creates user with passport "123"

        var getCustomerByPassportResponse = customersController.getCustomers("123");
        Assertions.assertEquals(HttpStatus.OK, getCustomerByPassportResponse.getStatusCode());
        Assertions.assertTrue(getCustomerByPassportResponse.hasBody());
    }

    @Test
    void givenNonExistingUserThen404() {
        var getCustomerResponse = customersController.getCustomer("POTATO");
        Assertions.assertEquals(HttpStatus.NO_CONTENT,getCustomerResponse.getStatusCode());
    }


}
