# TRAVEL AGENCY KATA


## Principios SOLID utilizados

En este proyecto he identificado los siguientes principios:

**Open/Closed Principle (OCP)**
Se observa en el diseño del `CustomersRepository`.
* *Justificación:* El sistema permite añadir nuevas formas de guardar datos creando nuevas clases que implementen la interfaz, sin necesidad de modificar el código existente en los Controladores o Comandos.

**Liskov Substitution Principle (LSP)**
Se cumple en las implementaciones del repositorio `InMemory` y `Database`.
* *Justificación:* Ambas implementaciones respetan el contrato de la interfaz `CustomersRepository`. Se puede intercambiar una por otra y el sistema sigue funcionando correctamente sin errores inesperados.

**Dependency Inversion Principle (DIP)**
Se observa en el constructor de `CustomersController`.
* *Justificación:* El controlador depende de la abstracción `CustomersRepository` (interfaz), no de implementaciones concretas. La implementación se inyecta externamente vía Spring, invirtiendo la dependencia: la infraestructura depende del dominio, no al revés. Esto permite cambiar la persistencia sin modificar el controlador.


## Patrones de Diseño

### Patrones de Creación
* **Builder Pattern:**
    * **Ubicación:** Anotación `@Builder` de Lombok.
    * **Justificación:** Facilita la creación de objetos complejos con muchos atributos, haciendo el código más legible que un constructor con múltiples parámetros.
* **Singleton Pattern:**
    * **Ubicación:** `CustomersInMemoryRepository` y las anotaciones `@Service`, `@RestController`, `@Repository`.
    * **Justificación:** Garantiza una única instancia de los servicios y repositorios para optimizar recursos y, en el caso del repositorio en memoria, mantener el estado.


### Patrones Estructurales
* **Adapter Pattern:**
    * **Ubicación:** `CustomersDatabaseRepository`.
    * **Justificación:** Esta clase actúa como un adaptador que convierte la interfaz de `CustomersJPARepository` a la interfaz `CustomersRepository`.


### Patrones de Comportamiento
* **DTO (Data Transfer Object):**
    * **Ubicación:** `PutCustomerDTO`, `GetCustomerDTO`.
    * **Justificación:** Desacoplan la estructura de la API REST del modelo de dominio interno.