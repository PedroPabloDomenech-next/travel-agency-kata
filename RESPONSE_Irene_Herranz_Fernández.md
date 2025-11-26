#RESPONSE_ Irene Herranz Fernández

## 1. Principios SOLID que he usado

- **SRP (Single Responsibility)**: Cada clase hace solo una cosa. Por ejemplo, `Customer` solo guarda los datos del cliente, y `CreateCustomerCommand` solo se encarga de crear un cliente.  
- **OCP (Open/Closed)**: El código está preparado para cambios en la persistencia sin tocar la lógica del negocio. Podemos usar memoria, JPA o cualquier otra base de datos sin cambiar los comandos o consultas.  
- **LSP (Liskov)**: Puedo usar cualquier implementación de `CustomersRepository` sin que falle la aplicación.  
- **ISP (Interface Segregation)**: Las interfaces solo tienen los métodos que realmente necesitamos.  
- **DIP (Dependency Inversion)**: La capa de aplicación depende de la interfaz `CustomersRepository`, no de la implementación concreta.

---

## 2. Patrones de diseño que he identificado

- **Arquitectura Hexagonal (Ports & Adapters)**: La lógica de negocio está separada de la infraestructura. La capa de dominio y aplicación no sabe si los datos vienen de memoria, JPA o cualquier otro sitio.  
- **Command Pattern**: `CreateCustomerCommand` encapsula la acción de crear un cliente en un objeto con su propio método `handle()`.  
- **Query Pattern**: `GetCustomerQuery` hace lo mismo para consultar clientes.  
- **Adapter Pattern**: El adaptador JPA (`CustomersRepositoryJPAAdapter`) conecta la interfaz de dominio con la base de datos.  
- **Builder Pattern**: Se usa en `Customer` y los DTOs para crear objetos de forma clara y ordenada.

---

## 3. Cambios que he hecho

- He creado un adaptador JPA para conectar `CustomersRepository` con `CustomersJPARepository`.  
- He añadido el mapeo entre `CustomerEntity` y `Customer` usando un pequeño mapper.  
- He inicializado por defecto `enrollmentDate` con la fecha actual y `active` en `true` cuando se crea un cliente.  
- Los tests funcionan igual que antes y ahora los clientes se guardan en la base de datos H2 (que emula PostgreSQL).

---

## 4. Resumen

- La aplicación sigue usando Arquitectura Hexagonal, y la lógica de negocio está separada de la infraestructura.  
- Ahora se pueden guardar y consultar clientes usando JPA, pero también se podría cambiar a otra tecnología sin modificar los comandos ni las consultas.  
- He respetado los principios SOLID y los patrones de diseño que ya existían en el proyecto.  


## 5. Cambios realizados y configuraciones que he tenido que hacer

1. **Versión del SDK y JDK**
   - Para que el proyecto funcionara en IntelliJ con Spring Boot 3.4.2, tuve que descargar un **SDK más reciente**.

2. **Cambio en la clase `CustomerEntity`**
   - Hibernate necesita un **constructor vacío público** para poder crear las entidades, así que añadí este constructor:
     ```java
     public CustomerEntity() {}
     ```
   - Sigo usando `@Builder` de Lombok para poder crear objetos fácilmente en el mapper, pero el constructor completo de todos los argumentos se hace privado para que no choque con JPA:
     ```java
     @Builder
     @AllArgsConstructor(access = AccessLevel.PRIVATE)
     ```
   - Con esto conseguimos:
     - Que JPA pueda instanciar la entidad sin problemas.
     - Seguir usando `CustomerEntity.builder()` en el mapper para convertir entre dominio y entidad.

3. **Resultado**
   - Ahora la app compila correctamente.
   - Los clientes se guardan y se leen de la base de datos H2 simulando PostgreSQL.
   - Todos los tests funcionan bien y no da errores de inicialización.





