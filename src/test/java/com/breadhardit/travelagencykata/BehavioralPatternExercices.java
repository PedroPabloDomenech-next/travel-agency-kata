package com.breadhardit.travelagencykata;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.breadhardit.travelagencykata.BehavioralPatternExercices.Travel.SCHENGEN_COUNTRIES;

@Slf4j
public class BehavioralPatternExercices {
    public static boolean isInSchengenSpace(String country) {
        return SCHENGEN_COUNTRIES.contains(country);
    }

    @Test
        // When customer buy a new Travel we have to scan the proper documentation
    void travelAgency() {
        List<Travel> travels = List.of(
                new Travel(UUID.randomUUID().toString(), "PYRAMIDS TOUR", "Spain", "EGYPT"),
                new Travel(UUID.randomUUID().toString(), "LISBOA TOUR", "Spain", "Portugal"),
                new Travel(UUID.randomUUID().toString(), "LISBOA TOUR", "Portugal", "Portugal")
        );
        for (Travel travel : travels) {
            if (travel.visaRequiredTravel) scanVisa();
            else if (travel.schengenSpaceTravel) scanPassport();
            else if (travel.sameCountryTravel) scanDNI();
        }
    }

    @Test
    @SneakyThrows
    public void companyTest() {
        EmployeesRepository employeesRepository = new EmployeesRepository();
        GreetingsNotificator greetingsNotificator = new GreetingsNotificator(employeesRepository);
        new Thread(() -> greetingsNotificator.applyNotifications()).start();
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("1").name("Pepe").email("pepe@pepemail.com").build());
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("2").name("Juan").email("pepe@pepemail.com").build());
    }

    /* EXERCISE 1
        Travels has an origin and a destination. Travels have some restrictions:
        - Travels with origin and destination in the same country require only Identity Document
        - Travels with origin and destination in schengen space, requires Passport
        - Travels with origin or destination out of schengen space, requires Visa
     */
    @Data
    public abstract static class Travel {
        public static final List<String> SCHENGEN_COUNTRIES = List.of("Spain", "France", "Iceland", "Italy",
                "Portugal");
        String id;
        String name;
        String origin;
        String destination;

        public Travel(String name, String origin, String destination) {
            this.id = UUID.randomUUID().toString();
            this.name = name;
            this.origin = origin;
            this.destination = destination;
        }

        public Travel(String origin, String destination) {
            this("", origin, destination);
        }

        abstract void scanDocumentation();
    }

    public static class LocalTravel extends Travel {

        public LocalTravel(String origin, String destination) {
            super(origin, destination);
        }

        @Override
        public void scanDocumentation() {
            log.info("Scanning National Identifier Document...");
        }
    }

    public static class SchengenSpaceTravel extends Travel {

        public SchengenSpaceTravel(String origin, String destination) {
            super(origin, destination);
        }

        @Override
        public void scanDocumentation() {
            log.info("Scanning Passport...");
        }
    }

    public static class InternationalTravel extends Travel {

        public InternationalTravel(String origin, String destination) {
            super(origin, destination);
        }

        @Override
        public void scanDocumentation() {
            log.info("Scanning VISA...");
        }
    }

    public static class TravelFactory {
        /**
         * PROBLEMA: Utilizar un String o un Enum que crece al agregar un tipo nuevo viola el OCP
         * SOLUCIÓN: Registro dinámico (investigar)
         */
        public static Travel createTravel(String origin, String destination) {
            if (origin.equals(destination))
                return new LocalTravel(origin, destination);
            else if (isInSchengenSpace(origin) && isInSchengenSpace(destination))
                return new SchengenSpaceTravel(origin, destination);
            else if (!isInSchengenSpace(origin) || !isInSchengenSpace(destination))
                return new InternationalTravel(origin, destination);
            else throw new IllegalArgumentException("Invalid Travel arguments");
        }
    }

    /*
     * When a new employee is enrolled, company sends a greetins e-mail.
     * A notification service is querying the database every second looking for new employees to notify
     */

    @Builder
    @Data
    public static class Employee {
        final String id;
        final String name;
        final String email;
        @Builder.Default
        Boolean greetingDone = Boolean.FALSE;
    }

    public static class EmployeesRepository {
        private static final ConcurrentHashMap<String, Employee> EMPLOYEES = new ConcurrentHashMap<>();

        public void addEmployee(Employee employee) {
            EMPLOYEES.put(employee.getId(), employee);
        }

        public List<Employee> getUnnotifiedEmployees() {
            return EMPLOYEES.values().stream().filter(e -> !e.greetingDone).toList();
        }
    }

    @Value
    @AllArgsConstructor
    public static class GreetingsNotificator {
        EmployeesRepository employeesRepository;

        @SneakyThrows
        public void applyNotifications() {
            while (true) {
                log.info("Aplying notifications");
                List<Employee> employeesToNotify = employeesRepository.getUnnotifiedEmployees();
                employeesToNotify.forEach(e -> {
                    log.info("Notifying {}", e);
                    e.setGreetingDone(Boolean.TRUE);
                });
                Thread.sleep(100);
            }
        }
    }
    // Use the proper behavioral pattern to avoid the continuous querying to database
}
