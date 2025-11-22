package com.breadhardit.travelagencykata;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
class CreationalPatternExercices {
    /*
     * Banking accounts has movements. And each movement can be a deposit or withdrawal
     * After a few months operating, we need to create new types of movements:
     *   - TRANSFER: It's a withdrawal, but we need the destination account number
     *   - ANNULMENT: It cancels a movement, so, we need the original movement
     */

    @Data
    /*
     Las clases abstractas se consideran incompletas por defecto; puede implementar la interfaz sin definir todos
     sus métodos, siempre y cuando sus clases hijas sí lo hagan. Por tanto, solo implementa el getId() y delega la
     implementación de apply() y revert() a sus clases hija.
     (De hecho, IntellIJ menciona esto en la alerta cuando declaras la clase como concreta en lugar de abstracta).
    */
    public abstract static class Movement{
        private final String id;
        private final String description;

        abstract void apply(Account account);
        abstract void revert(Account account);
    }

    public static class DepositMovement extends Movement  {
        private final Long amount;

        private DepositMovement(String id, String description, Long amount) {
            super(id, description);
            this.amount = amount;
        }

        private DepositMovement(Long amount) {
            this(UUID.randomUUID().toString(), "", amount);
        }

        @Override
        public void apply(Account account){
            account.balance += amount;
        }

        @Override
        public void revert(Account account){
            new WithdrawalMovement(UUID.randomUUID().toString(), "", amount).apply(account);
        }
    }

    public static class WithdrawalMovement extends Movement  {
        private final Long amount;

        private WithdrawalMovement(String id, String description, Long amount) {
            super(id, description);
            this.amount = amount;
        }

        private WithdrawalMovement(Long amount) {
            this(UUID.randomUUID().toString(), "", amount);
        }

        @Override
        public void apply(Account account){
            account.balance -= amount;
        }

        @Override
        public void revert(Account account){
            new DepositMovement(UUID.randomUUID().toString(), "", amount).apply(account);
        }
    }

    public static class TransferMovement extends Movement {
        private final Account destination;
        private final Long amount;

        private TransferMovement(String id, String description, Account destination, Long amount) {
            super(id, description);
            this.destination = destination;
            this.amount = amount;
        }

        private TransferMovement(Account destination, Long amount) {
            this(UUID.randomUUID().toString(), "Transfer to " + destination, destination, amount);
        }

        @Override
        public void apply(Account source){
            new WithdrawalMovement(this.amount).apply(source);
            new DepositMovement(this.amount).apply(this.destination);
        }

        @Override
        public void revert(Account source){
            /*
             Preguntar si es correcto esto: realiza una transferencia desde una cuenta que no es tuya solo para
             devolverte el dinero de la transferencia que quieres deshacer...
            */
            new TransferMovement(
                    UUID.randomUUID().toString(),
                        "Revert transfer to " + this.destination,
                    source,
                    this.amount
            ).apply(destination);
        }
    }

    public static class Annulment extends Movement {

        private final Movement movementToRevert;

        private Annulment(String id, String description, Movement movementToRevert) {
            super(id, description);
            this.movementToRevert = movementToRevert;
        }

        private Annulment(Movement movementToRevert) {
            this(UUID.randomUUID().toString(), "", movementToRevert);
        }

        @Override
        public void apply(Account account){
            this.movementToRevert.revert(account);
        }

        @Override
        public void revert(Account account){
            this.movementToRevert.apply(account);
        }
    }

    public static class MovementFactory{
        /**
         * PROBLEMA: La factoría recibe parámetros que no siempre van a usarse, puesto que cada tipo de movimiento
         * tiene parámetros que no siempre son coincidentes
         * SOLUCIÓN: Factory + Builder, de forma que:
         * - Factory decide el tipo a crear
         * - Builder gestiona los parámetros necesarios para crear dicho objeto, logrando que no se pasen atributos
         * nulos que no van a usarse dependiendo del tipo de objeto creado
         */
        public static Movement createMovement(String type,
                                              String description,
                                              Long amount,
                                              Account destination,
                                              Movement originalMovement){
            return switch(type.toUpperCase()){
                case "DEPOSIT" -> new DepositMovement(UUID.randomUUID().toString(), description, amount);
                case "WITHDRAWAL" -> new WithdrawalMovement(UUID.randomUUID().toString(), description, amount);
                case "TRANSFER" -> new TransferMovement(UUID.randomUUID().toString(), description, destination, amount);
                case "ANNULMENT" -> new Annulment(UUID.randomUUID().toString(), description, originalMovement);
                default -> throw new UnsupportedOperationException("Unknown movement type");
            };
        }
    }

    @Data
    @RequiredArgsConstructor
    public static class Account {
        public static final Map<String, Movement> MOVEMENTS = new ConcurrentHashMap<>();
        private final String id;
        private Long balance = 0L;

        public void addMovement(Movement movement) {
            MOVEMENTS.put(movement.getId(), movement);
            movement.apply(this);
            log.info("Current balance: {}",balance);
        }
    }
    @Test
    void test() {
        // ...
    }

}
