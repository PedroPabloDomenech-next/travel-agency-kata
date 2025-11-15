# SOLUCION PROPUESTA

Para arreglar el problema, se ha aplicado el método ADAPTER.

Sin embargo (LO SIENTO), he tenido muchos problemas con los nulos. En clase logré arreglar el de las fechas poniendo el now, pero el de Active no, por lo que lo entrego así tristemente.
He intentado probar poniendo el valor a true pero me sigue diciendo que estoy intentando insertar un nulo, así que no sé qué más hacer.

# SOLID:

## Single Responsability:

La clase GetCustomerQuery tiene dos responsabilidades (datos de consulta y lógica de manejo). Para resolverlo he cambiado código en el constructor, tras mover la lógica de manejo a una nueva clase GetCustomerQueryHandler.

## Dependency Inversion:

CustomersController no debería depender de CreateCustomerCommand y GetCustomerQuery (al ser estos módulos de bajo nivel), sino de abstracciones (ambos).
Para corregirlo, en lugar de crear la clase de comando con su lógica dentro, se usará el Handler que creamos para el Single Responsability en el apartado anterior.

## Open-Closed:

Resulta evidente que queda abierto a extensión, y cerrado a modificación gracias a @Value (que tras investigar, establece los atributos como privados).

## Liskov:

La implementación del repositorio en memoria (CustomersInMemoryRepository) puede sustituir al Port (CustomersRepository) sin alterar el comportamiento de los comandos/queries en la capa Application. La nueva implementación JPA (CustomersJPAAdapter) debe hacer lo mismo.

## Interface segregation:

En este caso en particular, se me ocurre poco que hacer con interfaces más allá de lo que ya tenemos, y no aportaría mucho hacer nuevas interfaces, por lo que no modificaré nada.
Siguiendo el diagrama de clases original, sí que se puede crear dos interfaces para el controlador de clientes y para el comando de creación, pero aportaría muy poco (y habiendo tanto código cambiado, menos aún) por lo que no hago cambios en esto.

# PATRONES DE DISEÑO:

## Architecture Pattern:
Hexagonal: El proyecto está dividido en Domain, Application (Ports) e Infrastructure (Adapters). El Port es la interfaz CustomersRepository. El Adapter es la clase CustomersInMemoryRepository (y la nueva CustomersJPAAdapter).

## Creational Pattern:
Builder: Se utiliza la anotación @Builder de Lombok en clases como Customer, CreateCustomerCommand, y GetCustomerQuery para construir objetos de forma fluida y clara.

## Structural Pattern:
Adapter	(Implementado en mi solución): Se requiere para conectar la interfaz del Port (CustomersRepository) con la interfaz de la infraestructura (CustomersJPARepository), traduciendo objetos de Domain a Entity y viceversa a través de la clase CustomersJPAAdapter.

## Creational Pattern:
Repository: El CustomersRepository actúa como un Repository para separad la lógica de negocio de la persistencia de los datos.