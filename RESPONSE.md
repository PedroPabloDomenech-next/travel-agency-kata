# Desarrollo de la respuesta al taller Patrones de Diseño

## 1. Implementación de persistencia basada en Base de Datos

Para lograr utilizar la interfaz [CustomersJPARepository.java]() en la aplicación, que usa el port 
[CustomerRepository.java](), y por tanto poder persistir los datos usando Spring JPA en una base de datos, he 
utilizado el 
patrón de diseño 
Adapter (implementado por la clase [CustomersInDatabaseRepository]()).
Dicha clase
asumido la 
responsabilidad de 
convertir la salida de la primera interfaz para que implementase la segunda, pudiendo así ser utilizada por la 
aplicación. En concreto, se encarga de lo siguiente:

- Realiza transformaciones entre las clases del dominio ([Customer]()) y las clases entidad que almacena la base de 
  datos ([CustomerEntity]()).
- Envuelve el resultado utilizando el genérico Optional<>, tal y como define la interfaz.

En definitiva, se encarga de implementar la interfaz [CustomerRepository](), pero delegando la funcionalidad en la 
comunicación de Spring con la base de datos mediante el JPA y asumiendo la tarea de adaptar su salida para cumplir 
con el contrato del puerto que usa la aplicación.

### 1.2 Anotaciones importantes

- La clase [TravelAgencyKataApplicationTests]() dada tenía un error: declaraba la respuesta recibida al obtener la 
  referencia a Customer por número de pasaporte, pero no realizaba aserciones sobre ella, sino sobre la respuesta 
  recibida al buscarlo por id, la cual era comprobada justo en las líneas anteriores. De esta forma, se realizaban 
  dos veces aserciones sobre la respuesta basada en id, y ninguna sobre la basada en el número de pasaporte. Al 
  corregir esto, y sin haber implementado todavía la solución del problema (es decir, corriendo sobre el 
  respositorio en memoria que venía ya implementado) la parte del test relacionada con la búsqueda por número de 
  pasaporte falla, recibiendo un código 204 en lugar del 200 que se espera. La parte del test que comprueba la 
  búsqueda por id funciona correctamente.
- Se ha utilizado la etiqueta @Primary para definir qué repositorio se tiene que utilizar, por lo que 
  [CustomersInMemoryRepository]() puede seguir utilizándose, si se pasa la anotación a esta clase. Actualmente, se 
  encuentra en la clase [CustomersInDBRepository](), para que el test se ejecute sobre dicho repositorio.
- Se han refactorizado los test, separándolos por tipo de búsqueda. De esta manera, se aprecia mejor que la búsqueda 
  por id (tanto en la versión en memoria como en la solución por JPA) funciona correctamente, mientras que la 
  búsqueda por número de pasaporte falla en ambas.
- La clase [CustomersInDBRepository]() también inicializa, en caso de no haberse hecho, los atributos que se quedan 
  como nulos pertenecientes a Customer, ya que PutCustomerDTO no los contiene. Me di cuenta porque al ejecutar el 
  código, Hibernate me daba error, diciendo que se estaban guardando atributos nulos que no podían serlo. Revisando 
  la implementación, vi que la clase [CustomerController]() no los inicializaba al crear el objeto Customer, pues 
  dichos atributos no estaban en el DTO. Esto se ha hecho así para evitar modificar los DTO y agregarles atributos, 
  por lo que dicha responsabilidad ha pasado a la clase adaptadora.

# 2. Principios SOLID y Patrones de diseño en el código existente

## 2.1. Principios y patrones dentificados

- **Single Responsability Principle** y **Open-Closed Principle** aplicado mediante patrón **Command** en las clases 
[CustomerController]() y 
[CreateCustomerCommand]().

- **Single Responsability Principle** (principalmente) aplicado mediante patrón **Builder** (en prácticamente todas las 
clases, via anotación @Builder de Lombok Project).

- **Interface Segregation Principle** y **Dependency Inversion Principle** aplicado mediante las interfaces de las que 
depende el sistema, que son funcionales y permiten que el sistema dependa de abstracciones, como ocurre, por ejemplo,
con el repositorio (a través de [CustomerRepository]()).


## 2.2 Principios violados

Se han encontrado las siguientes violaciones de patrones SOLID:
- **Open-Closed Principle** en [CustomerController]() y [CreateCustomerQuery](), puesto que el código no permite su 
  extensión a nuevos parámetros de búsqueda tal y como está actualmente. Esto podría resolverse implementando el 
  patrón Strategy, y reforzar el **Single Responsability Principle** de la mano del patrón Factory. He intentado 
  implementar este cambio, pero debido a desconocimiento sobre el uso de los beans en Spring, los endpoints y que el 
  parámetro se toma de forma distinta según cuál sea, no he sido capaz de implementarlo con éxito. Estaría 
  encantado de conocer la respuesta.