package com.breadhardit.travelagencykata.domain;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Customer {
    String id;
    String name;
    String surnames;
    LocalDate birthDate;
    String passportNumber;
    LocalDate enrollmentDate;
    Boolean active;
}
