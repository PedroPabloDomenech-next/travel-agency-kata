Solución aplicada:
Se crea CustomersDatabaseRepository, que implementa CustomersRepository. El patron
de diseño utilizado fue Adapter, ya que se adapta la interfaz CustomersRepository a la nueva
implementación que utiliza JPA para interactuar con la base de datos.

Resumen de cambios:

Se añade un @NoArgsConstructor y un AllArgsConstructor a la clase CustomerEntity
para corregir este error: Class 'CustomerEntity' should have [public, protected] no-arg constructor

Se añade a la clase CustomersInMemoryRepository un profile "in-memory" para que pueda ser
activada dicha implementación en memoria mediante el perfil correspondiente. Además, soluciona
un error en CustomersController.

Se hizo un  cambio en CreateCustomerCommand en el que se agregaba el enrollmentDate y active al Handler, ya que el test givenAUserWhenCreatedThenOK
daba error porque la columna ENROLLMENT_DATE y la columna ACTIVE no podían ser nulas.

Patrones de diseño aplicados en el codigo:
- Singleton (Ejemplo: CustomersInMemoryRepository)
- Builder (Ejemplo: Casi todas las clases)
- Command (Ejemplo: CreateCustomerCommand)
- Adapter (Ejemplo: CustomersDatabaseRepository, el cual se acaba de crear)

Principios SOLID:
- SRP (Single Responsibility Principle): Cada clase tiene una única responsabilidad. Por ejemplo, en vez de crear una unica clase Customers que haga todo
    se crean varias clases con responsabilidades específicas como CustomersController, CustomersService, CustomersRepository, etc.
- OCP (Open/Closed Principle): Técnicamente se fuerza a aplicar este principio en la tarea, ya que se pide crear una nueva implementación de CustomersRepository sin modificar lo demas
- LSP (Liskov Substitution Principle): Todas las clases que implementan CustomersRepository hacen lo que CustomersRepository hace. Por tanto, en teoria se pueden sustituir sin problemas.
- ISP (Interface Segregation Principle): Interfaces que hacen lo que deben. CustomersRepository solo define los métodos necesarios para interactuar con los datos de los clientes.
- DIP (Dependency Inversion Principle): CustomersRepository no implementa nada, es una abstracción. Luego existen clases que implementan dicha abstracción.