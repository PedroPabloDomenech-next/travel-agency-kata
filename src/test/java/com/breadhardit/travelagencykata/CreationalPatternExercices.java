package com.breadhardit.travelagencykata;

import lombok.Builder;
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

    public interface Movement {
        String getId();
        void apply(Account account);
        void revert(Account account);
    }

    @Data
    @RequiredArgsConstructor
    public static class DepositMovement implements Movement {
        private final String id;
        private final Long amount;
        private final String description;

        @Override
        public void apply(Account account) {
            account.setBalance(account.getBalance() + amount);
        }
        @Override
        public void revert(Account account) {
            account.setBalance(account.getBalance() - amount);
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class WithdrawalMovement implements Movement {
        private final String id;
        private final Long amount;
        private final String description;

        @Override
        public void apply(Account account) {
            account.setBalance(account.getBalance() - amount);
        }
        @Override
        public void revert(Account account) {
            account.setBalance(account.getBalance() + amount);
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class TransferMovement implements Movement {
        private final String id;
        private final Long amount;
        private final String description;
        private final String destinationAccount;

        @Override
        public void apply(Account account) {
            account.setBalance(account.getBalance() - amount);
        }
        @Override
        public void revert(Account account) {
            account.setBalance(account.getBalance() + amount);
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class AnnulmentMovement implements Movement {
        private final String id;
        private final Movement originalMovement;

        @Override
        public void apply(Account account) {
            originalMovement.revert(account);
        }
        @Override
        public void revert(Account account) {
            originalMovement.apply(account);
        }
    }

    public static class MovementFactory {
        public static Movement createMovement(String id, String type, Long amount, String description, String descriptionAccount, Movement originalMovement) {
            return switch (type.toUpperCase()) {
                case "DEPOSIT" -> new DepositMovement(id, amount, description);
                case "WITHDRAWAL" -> new WithdrawalMovement(id, amount, description);
                case "TRANSFER" -> new TransferMovement(id, amount, description, descriptionAccount);
                case "ANNULMENT" -> new AnnulmentMovement(id, originalMovement);
                default -> throw new IllegalArgumentException("Unknown movement type: " + type);
            };
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
            movement.apply(this);
        }
    }
    @Test
    public void test() {
        Account account = new Account(UUID.randomUUID().toString());

        Movement deposit = MovementFactory.createMovement("1", "DEPOSIT", 1000L, "Salary", null, null);
        Movement withdrawal = MovementFactory.createMovement("2", "WITHDRAWAL", 10L, "Coffee", null, null);
        Movement transfer = MovementFactory.createMovement("3", "TRANSFER", 100L, "Rent", "DEST-1234", null);

        account.addMovement(deposit);
        account.addMovement(withdrawal);
        account.addMovement(transfer);

        Movement annulMovement = MovementFactory.createMovement("4", "ANNULMENT", null, "Revert coffee purchase", null, transfer);
        account.addMovement(annulMovement);

        Movement annulTransfer = MovementFactory.createMovement("5", "ANNULMENT", null, "Revert rent transfer", null, transfer);
        account.addMovement(annulTransfer);
    }
    /* TODO
        Made the refactor to create new movement types, and avoid scalability issues applying the proper creational pattern.
        Remember, our code MUST follow SOLID Principles, so refactor the classes you need to accomplish it
     */


}
