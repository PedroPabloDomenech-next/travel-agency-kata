package com.breadhardit.travelagencykata;

import com.breadhardit.travelagencykata.domain.Customer;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class BehavioralPatternExercices {
    /* EXERCISE 1
        Travels has an origin and a destination. Travels have some restrictions:
        - Travels with origin and destination in the same country require only Identity Document
        - Travels with origin and destination in schengen space, requires Passport
        - Travels with origin or destination out of schengen space, requires Visa
     */
    @NoArgsConstructor
    public static class TravelFactory{
        public static final List<String> SCHENGEN_COUNTRIES = List.of("Spain","France","Iceland","Italy","Portugal");
        public Travel buildTravel(String name, String origin, String destination){
            if(origin.equals(destination))
                return RegionalTravel.builder().name(name).origin(origin).destination(destination).build();
            else if(SCHENGEN_COUNTRIES.contains(origin) && SCHENGEN_COUNTRIES.contains(destination))
                return SchengenTravel.builder().name(name).origin(origin).destination(destination).build();
            else return VisaRequiredTravel.builder().name(name).origin(origin).destination(destination).build();

        }
    }
    @Data
    @SuperBuilder
    public static abstract class Travel {
        @Builder.Default
        String id = UUID.randomUUID().toString();
        String name;
        String origin;
        String destination;
        public abstract void scanDocument();
    }
    @SuperBuilder
    public static class RegionalTravel extends Travel {
        @Override
        public void scanDocument() {
            log.info("Scanning DNI ...");
        }
    }
    @SuperBuilder
    public static class SchengenTravel extends Travel {
        @Override
        public void scanDocument() {
            log.info("Scanning passport ...");
        }
    }
    @SuperBuilder
    public static class VisaRequiredTravel extends Travel {
        @Override
        public void scanDocument() {
            log.info("Scanning visa ...");
        }
    }
  @Test
    // When customer buy a new Travel we have to scan the proper documentation
    public void travelAgency() {
        TravelFactory factory = new TravelFactory();
        List<Travel> travels = List.of(
                factory.buildTravel("PYRAMIDS TOUR","Spain","EGYPT"),
                factory.buildTravel("LISBOA TOUR","Spain","Portugal"),
                factory.buildTravel("LISBOA TOUR","Portugal","Portugal")
        );
        for (Travel travel: travels) {
           travel.scanDocument();
        }
    }
    // Refactor code using the proper structural pattern


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
    public static class EmployeesRepository{
        private static final ConcurrentHashMap<String,Employee> EMPLOYEES = new ConcurrentHashMap<>();
        private static final List<NotificationObserver> NOTIFICATION_OBSERVERS = new ArrayList<>();
        public void addNotificationObserver(NotificationObserver observer) {
            NOTIFICATION_OBSERVERS.add(observer);
        }
        public void addEmployee(Employee employee) {
            EMPLOYEES.put(employee.getId(),employee);
        }
        public List<Employee> getUnnotifiedEmployees() {
            return EMPLOYEES.values().stream().filter(e -> !e.greetingDone).toList();
        }

        public void patchEmployee(Employee employee) {
            EMPLOYEES.put(employee.getId(),employee);
        }
    }

    @Value
    public static class Notification{
        String email;
        String text;
    }

    public interface NotificationObserver{
       void notify(Employee employee, Notification greetingsNotification);
    }

    @RequiredArgsConstructor
    public static class GreetingsNotificationObserver implements NotificationObserver{
        final EmployeesRepository employeesRepository;

        @Override
        public void notify(Employee employee, Notification greetingsNotification) {
            log.info("Sending emailt to {} with content: {}", greetingsNotification.getEmail(), greetingsNotification.getText());
            log.info("Updating customer");
            employeesRepository.patchEmployee(employee);
        }
    }

    @Test
    @SneakyThrows
    public void companyTest() {
        EmployeesRepository employeesRepository = new EmployeesRepository();
        GreetingsNotificationObserver greetingsNotificationObserver = new GreetingsNotificationObserver(employeesRepository);
        employeesRepository.addNotificationObserver(greetingsNotificationObserver);
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("1").name("Pepe").email("pepe@pepemail.com").build());
        Thread.sleep(200);
        employeesRepository.addEmployee(Employee.builder().id("2").name("Juan").email("pepe@pepemail.com").build());
    }
    // Use the proper behavioral pattern to avoid the continuous querying to database
}
