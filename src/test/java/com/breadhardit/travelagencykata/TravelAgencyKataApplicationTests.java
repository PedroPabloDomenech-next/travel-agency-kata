package com.breadhardit.travelagencykata;

import com.breadhardit.travelagencykata.infrastructure.persistence.repository.CustomersJPARepository;
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
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootTest
@Slf4j
@DirtiesContext
class TravelAgencyKataApplicationTests {

    @Autowired
    CustomersController customersController;

    // el @Autowired de customerJPARepository sobra: no se utiliza

    private String exampleId;

    @BeforeEach
    void setUp() {
        var putCustomerResponse = customersController.putCustomer(
                PutCustomerDTO.builder()
                        .name("Pepe")
                        .surnames("Perez")
                        .birthDate(LocalDate.of(1980, 1, 1))
                        .passportNumber("123")
                        .build()
        );

        String location = Objects.requireNonNull(putCustomerResponse.getHeaders().getLocation()).getPath();
        exampleId = location.substring(location.lastIndexOf("/") + 1);
    }

    @Test
    void contextLoads() {
        log.info("Context Loaded");
    }

    @Test
    void givenExampleUserWhenPutThenOk() {
        var putCustomerResponse = customersController.putCustomer(
                PutCustomerDTO.builder()
                        .name("Juan")
                        .surnames("García")
                        .birthDate(LocalDate.of(1980, 1, 6))
                        .passportNumber("456")
                        .build()
        );

        Assertions.assertEquals(HttpStatus.CREATED, putCustomerResponse.getStatusCode());
        Assertions.assertTrue(putCustomerResponse.getHeaders().containsKey(HttpHeaders.LOCATION));
    }

    @Test
    void givenExampleUserInRepositoryWhenGetByIdThenOk() {
        var getCustomerResponse = customersController.getCustomer(exampleId);
        Assertions.assertEquals(HttpStatus.OK, getCustomerResponse.getStatusCode());
        Assertions.assertTrue(getCustomerResponse.hasBody());
    }

    @Test
    void givenExampleUserInRepositoryWhenGetByNumberPassportThenOk() {
        var getCustomerByPassportResponse = customersController.getCustomers("123");
        Assertions.assertEquals(HttpStatus.OK, getCustomerByPassportResponse.getStatusCode());
        Assertions.assertTrue(getCustomerByPassportResponse.hasBody());
    }

    @Test
    void givenNonExistingUserThen204() {
        var getCustomerResponse = customersController.getCustomer("POTATO");
        Assertions.assertEquals(HttpStatus.NO_CONTENT, getCustomerResponse.getStatusCode());
    }
}