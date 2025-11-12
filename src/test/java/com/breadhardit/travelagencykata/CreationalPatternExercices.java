package com.breadhardit.travelagencykata;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CreationalPatternExercices {
    /*
     * Banking accounts has movements. And each movement can be a deposit or withdrawal
     * After a few months operating, we need to create new types of movements:
     *   - TRANSFER: It's a withdrawal, bet we need the destination account number
     *   - ANNULMENT: It cancels a movement, so, we need the original movement
     */
    @Data
    @RequiredArgsConstructor
    public static abstract class Movement implements IMovement {
        String id;
        Long amount;
        String description;

        public Movement(String id, Long amount, String description) {
            this.id = id;
            this.amount = amount;
            this.description = description;
        }
    }
    public interface IMovement {
        void performMovement(Account account);
        void undoMovement(Account account);
    }

    @RequiredArgsConstructor
    public static class MovementDeposit extends Movement implements IMovement {

        public MovementDeposit(String id, Long amount, String description) {
            super(id, amount, description);
        }

        @Override
        public void performMovement(Account account) {
            account.setBalance(account.getBalance() + amount);
        }
        @Override
        public void undoMovement(Account account) {
            account.setBalance(account.getBalance() - amount);
        }
    }

    @RequiredArgsConstructor
    public static class MovementWithdrawal extends Movement implements IMovement {

        public MovementWithdrawal(String id, Long amount, String description) {
            super(id, amount, description);
        }

        @Override
        public void performMovement(Account account) {
            account.setBalance(account.getBalance() - amount);
        }

        @Override
        public void undoMovement(Account account) {
            account.setBalance(account.getBalance() + amount);
        }
    }

    @RequiredArgsConstructor
    public static class MovementTransfer extends Movement implements IMovement {
        Account destination;

        public MovementTransfer(String id, Long amount, String description,Account destination) {
            super(id, amount, description);
            this.destination = destination;
        }

        @Override
        public void performMovement(Account account) {
            account.setBalance(account.getBalance() - amount);
            destination.setBalance(destination.getBalance() + amount);
        }

        @Override
        public void undoMovement(Account account) {
            account.setBalance(account.getBalance() + amount);
            destination.setBalance(destination.getBalance() - amount);
        }
    }

    @RequiredArgsConstructor
    public static class MovementAnnulement extends Movement implements IMovement {
        Movement original;

        public MovementAnnulement(String id, String description,Movement original) {
            this.id = id;
            this.description = description;
            this.original = original;
        }

        @Override
        public void performMovement(Account account) {
            original.undoMovement(account);
        }

        @Override
        public void undoMovement(Account account) {
            original.performMovement(account);
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class Account {
        public static final ConcurrentHashMap<String,Movement> MOVEMENTS = new ConcurrentHashMap<>();
        final String id;
        Long balance = 0L;

        public void addMovement(Movement movement) {
            MOVEMENTS.put(movement.getId(),movement);
            movement.performMovement(this);
            log.info("Current balance: {}",balance);
        }
    }
    public static class MovementFactory{
        public static Movement createMovement(String id, String type, Long amount, String description, Account destination, Movement original) {
          return switch (type.toUpperCase()){
                case "DEPOSIT" -> new MovementDeposit(id,amount,description);
                case "WITHDRAWAL" -> new MovementWithdrawal(id,amount,description);
                case "TRANSFER" -> new MovementTransfer(id, amount,description,destination);
                case "ANNULMENT" -> new MovementAnnulement(id,description, original);
                default  -> throw new IllegalStateException("Unknown movement type: " + type);

            };
        }
    }

    @Test
    public void test() {
        Account account = new Account(UUID.randomUUID().toString());
        Account account2 = new Account(UUID.randomUUID().toString());

        Movement deposit = MovementFactory.createMovement("1", "DEPOSIT", 1000L,"Salary",null,null);
        Movement withdrawal = MovementFactory.createMovement("2", "WITHDRAWAL", 19L,"Coffe",null,null);
        Movement transfer = MovementFactory.createMovement("3", "TRANSFER", 100L,"Rent",account2,null);

        account.addMovement(deposit);
        account.addMovement(withdrawal);
        account.addMovement(transfer);

        Movement annulWithdrawal = MovementFactory.createMovement("4","ANNULMENT", null,"Revert coffe purchase",null,withdrawal);
        account.addMovement(annulWithdrawal);

        Movement annulTransfer = MovementFactory.createMovement("5","ANNULMENT", null,"Revert rent transfer",null,transfer);
        account.addMovement(annulTransfer);
    }
    /* TODO
        Made the refactor to create new movement types, and avoid scalability issues applying the proper creational pattern.
        Remember, our code MUST follow SOLID Principles, so refactor the classes you need to accomplish it
     */


}
