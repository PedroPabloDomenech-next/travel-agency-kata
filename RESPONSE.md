# PATRONES DE DISEÑO APLICADOS

## 1. Adapter (Patrón esctructural):

Este lo he decidido aplicar ya que hacemos que dos interfaces
incompatibles puedan trabajar juntas. En este proyecto,
hay que adaptar CustomersJPARepository a la interfaz 
CustomersRepository.

Con el patron Adapter, conseguimos una implementación de
CustomerRepository que envuelve a CustomersJPARepository. 
Con la interfaz CustomersRepository, hacemos que no dependa
de bases de datos altamente acopladas.

Además, este patrón perimitiría cambiar JPA por cualquier
otra tecnología en el futuro.

Por último, podemos ver que al hacer esto, se cumple el 
principio de Inversión de Dependencias.


## 2. Facade (Patrón esctructural)

Este patrón también lo he aplicado yo, ya que lo he visto
necesario en la clase CustomersController.
El patrón Fecade simplifica el acceso en esta clase,
la cual tiene varias responsabilidades en una misma
clase, violando el principio de Single Responsability.

Al haber introducido la clase CustomerFacade que encapsula
toda la lógica, simplifica CustomerController a recibir la 
peticion, delegar en la fachada y devolver la respuesta. Para
asi poder cumplir con el principio de Single Responsability.

## 3. Builder (Patrón creacional)

Este patrón no lo he aplicado yo, sino que lo he identificado
ya en varias clases como Customer, CustomerEntity, GetCustomerDTO y
PutCustomerDTO. Aunque no se haga con un método builder, vemos 
que lo aplica mediante la librería Lombok, la cual lo genera 
automaticamente mediante la etiquera @Builder.

Esto significa que nuestro código queda mucho más limpio,
evitando constructores enormes.

## 4. Command (Patrón comportamiento)

Al igual que el Builder, este patrón no lo he aplicado yo, sino 
que lo he identificado en las clases CreateCustomerCommand y 
GetCustomerQuery. El patrón Command nos permite encapsular
las operaciones de crear y buscar clientes dentro de un objeto,
donde llamamos al método hanlde() para realizar la operación.

Adicionalmente, podría crearse una clase base o interfaz 
Command con un método genérico handle(), y hacer que cada 
uno de los comandos concretos extendiese de ella, lo que nos 
ayudaría a registrar más comandos en el futuro.

# PRINCIPIOS SOLID

## 1. Single Responsability (SRP)

Este principio lo podemos ver en la clase Customer, ya que
solo modela, no tiene métodos. Al igual que la clase CustomerJPAAdapter,
ya que esta solo adapta el dominio. Además, hemos usado el 
patron Fachada para que este principio se tuviese en cuenta en 
la clase CustomersController. 

## 2. Open/Close (OCP)

Este principio lo podemos ver en la clase CustomerJPAAdapter,
ya que el dia que queramos cambiar a otra base de datos solo
reemplazamos el adapter. 

## 3. Liskov Substitution (LSP)

El principio de Liskov lo podemos ver en la clase CustomersJPARepository,
ya que extiende a JpaRepository sin cambiar su comportamiento.

## 4. Interface Segregation (ISP)

El principio de Interface Segregation lo podemos ver en la
interfaz CustomersJPARepository, ya que contiene únicamente los
métodos que necesita la aplicación, sin exponer el repositorio
JPA completo.

## 5. Dependency Inversion (DIP)

El principio de Dependency Inversion lo podemos ver en la
clase CustomerJPAAdapter, ya que depende de CustomerRespository



