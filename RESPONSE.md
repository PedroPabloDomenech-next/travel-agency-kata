## LORENA PEÑAS PIQUERAS

### PATRONES DE DISEÑO
- Implementar un patrón de diseño estructural, en este caso **Adapter**, para adaptar la base de datos JPA con lo que devuelve ``CustomerRepository``.
La clase que hace de Adapter es ``RepositoryAdapter.java``, la cual implementa todos los métodos de ``CustomersRepository``, los cuales "transforman" ``CustomerEntity`` en ``Customer``

### PRINCIPIOS SOLID QUE SIGUE
- ``RepositoryAdapter.java`` sigue el principio de **Single Responsibility**, ya que su única función es transformar objetos entre el dominio (``Customer``) y la base de datos (``CustomerEntity``) y delegar el acceso a datos
- ``RepositoryAdapter.java`` sigue el principio de **Dependency Inversion** porque la aplicación depende de la abstracción `CustomersRepository` en lugar de una implementación concreta
- 

### PRINCIPIOS SOLID QUE NO SIGUE (se han cambiado)
- .

### CAMBIOS HECHOS A PARTE
- Separación de tests para facilitar detección de errores
