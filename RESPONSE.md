
---
# _PRÁCTICA DE PATRONES DE DISEÑO: LORENA PEÑAS_

---

## CLASES AÑADIDAS (explicadas posteriormente)
- ``RepositoryAdapter``
- ``CustomerTransformer``
- ``CustomerWritter``
- ``CustomerReaderByPassport``
- ``CustomerReaderById``

---

## PATRONES DE DISEÑO
### YA IMPLEMENTADOS ANTES DE LAS MODIFICACIONES
- Muchas de las clases (``CreateCustomerCommand``, ``CustomersController``, ``Customer``, etc) utilizan el patrón de diseño **Builder**
- ``CustomersInMemoryRepository`` sigue el patrón **Singleton** (``@Scope("singleton")``)
- ``CreateCustommerCommand`` implementa el patrón **Command** con el método handle()


### IMPLEMENTADOS POSTERIORMENTE
- Implementación de un patrón de diseño estructural en ``RepositoryAdapter``, en este caso **Adapter**, para adaptar la interfaz ``CustomerRepository`` para trabajar con JPA
- ``RepositoryAdapter`` sigue el patrón **Singleton**, ya que usa ``@Scope("singleton")``, para asegurar una única instancia.
- ``CustomerTransformer`` utiliza el patrón de diseño **Builder**
- ``CustomersRepository`` usa el patrón **Strategy** para definir la estrategia para acceso a datos

---

## PRINCIPIOS SOLID QUE SIGUE
### YA SEGUIDOS ANTES DE LAS MODIFICACIONES
- ``CreateCustomerCommand`` sigue el principio de **Single Responsibility**, al estar tener únicamente una responsibilidad: crear un cliente y devolver su ID

### SEGUIDOS TRAS LAS MODIFICACIONES
- ``RepositoryAdapter`` sigue el principio de **Single Responsibility**, ya que su única función es delegar el acceso a datos
- ``RepositoryAdapter`` sigue el principio de **Dependency Inversion** porque la aplicación depende de la abstracción `CustomersRepository` en lugar de una implementación concreta
- ``CustomerTransformer`` sigue el principio de **Single Responsibility**, ya que su única función es transformar objetos entre el dominio (``Customer``) y la base de datos (``CustomerEntity``)

---

## PRINCIPIOS SOLID QUE NO SIGUE
- La interfaz ``CustomersRepository`` infringe el principio de **Interface Segregation**, ya que la interfaz no es específica (puede haber clientes que solo se guardan o solo le leen, y se ven forzados a depender de métodos que pueden no ser usados)
--> Solución: Segregar la interfaz en unas más pequeñas y específicas (``CustomerWritter``, ``CustomerReaderByPassport`` y ``CustomerReaderById``)

---

## CAMBIOS HECHOS A PARTE
- Separación de tests para facilitar detección de errores
- Corrección de bug en tests proporcionados
