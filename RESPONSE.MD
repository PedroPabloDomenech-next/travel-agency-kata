# RESPONSE.md

## ¿Qué se pedía en el kata?

Básicamente, una app de una agencia de viajes donde los clientes se guardaban en memoria, ha decidido dar el paso a una Base de Datos real 
como PostgreSQL.
El problema es que:

- La parte de Application usa un puerto llamado `CustomersRepository`.
- JPA usa un repositorio distinto llamado `CustomersJPARepository`.
- Uno trabaja con `Customer` (dominio), el otro con `CustomerEntity` (BD).
- Por lo que hay que crear un adaptador

Además, como el enunciado indicaba que esto es arquitectura hexagonal, no se puede tocar Domain ni Application, solo Infrastructure.

Por lo que he hecho lo siguiente:
1. Que todo encaje usando patrones de diseño.
2. Revisar si se cumplen los principios **SOLID**.
3. Hacer que los clientes se guarden y se recuperen desde la Base de Datos.

---

## 1. Cambios

### 1. Crear un Adapter para conectar Application y la Base de datos

Esta clase es una especie de “traductor”:

- Convierte Customer → CustomerEntity para guardar en BD
- Convierte CustomerEntity → Customer para devolver al dominio
- Usa el `CustomersJPARepository` por dentro
- Y todo esto sin tocar Application ni Domain


###  2. Eliminar el repositorio en memoria
Había una clase llamada `CustomersInMemoryRepository` que usaba un HashMap.  
Como no sirve:

- He quitado la anotación `@Repository`  

Así Spring **solo usa el adaptador JPA**, que es lo que queremos.

###  3. Añadir un método en JPA
Application necesitaba buscar por pasaporte, así que añadimos en `CustomersJPARepository`:

```java
Optional<CustomerEntity> findByPassportNumber(String passportNumber);
```
### 4. Mapeos entre objetos

Como Customer y CustomerEntity no son lo mismo, he creado los métodos:

toEntity(Customer)

toDomain(CustomerEntity)

Con esto convertimos entre uno y otro y mantenemos el codigo limpio.

---
## 2. Principios SOLID en el proyecto (resumen)

### SRP — Cada clase hace una cosa

Customer: datos del cliente

CustomerEntity: datos para BD

CustomerAdapter: convierte y llama a JPA

Los commands solo hacen lógica de crear o buscar

Cada clase tiene su responsabilidad bien separada.

### OCP — Abierto a extensión, cerrado a modificación

Pudimos cambiar de “guardar en memoria” → “guardar en BD”
sin tocar Application ni Domain.
Solo añadimos una nueva implementación del puerto.

### LSP — Sustitución

El sistema funcionaba antes con el repositorio en memoria.
Ahora funciona con el adaptador JPA.
Los dos implementan la misma interfaz → cumplen LSP.

### ISP — Interfaces pequeñas

CustomersRepository solo tiene 3 métodos.
No obliga a implementar cosas que no necesitamos.

### DIP — Las capas importantes dependen de interfaces

Commands dependen de CustomersRepository

Controller también
Application nunca depende de JPA directamente.
Spring inyecta la versión que toca (el adaptador).

## 3. Patrones de diseño usados
   
### Adapter (principal)

Sirve para “hacer encajar” dos interfaces distintas.
Domain usa una, JPA otra.
El adaptador las conecta.

### Arquitectura hexagonal

Todo está dividido en:

Domain

Application

Infrastructure

### Command Pattern

CreateCustomerCommand

GetCustomerQuery

Cada uno es un caso de uso.

### Builder (de Lombok)

Para crear objetos sin constructores gigantes.

## 4. Resumen general

Se ha creado CustomerAdapter para usar BD.

Después añadí findByPassportNumber en el repo JPA.

Hice los mapeos entre dominio y entidad.

Quité el repositorio en memoria.

Comprobé la aplicación de los SOLID y está todo limpio.

En resumen, hemos pasado de guardar clientes “a lo cutre” en memoria a guardarlos en una base de datos real sin romper nada del código principal.
La clave fue crear un Adapter para que Application siga funcionando sin enterarse de que ahora usamos BD.
Todo se hizo respetando SOLID y la arquitectura hexagonal.

