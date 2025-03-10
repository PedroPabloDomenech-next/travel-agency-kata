### PRINCIPIOS SOLID YA APLICADOS EN EL PROGRAMA
Single responsibility: las clases tienen un único propósito y no hay clases con más de una función.
Open/close: no se cumple del todo porque hay que modificar el código base para añadir la nueva funcionalidad. Además, en la clase GetCustomerQuery se viola este principio, ya que si se añadiera una nueva forma de buscar un Customer se tendría que modificar esta clase.
Sustitucion de Liskov: no hay herencia de clases, pero se cumple al poder añadir cualquier clase que cumpla la interfaz CustomerRepository sin cambiar el comportamiento de las demás clases. 
Como no hay un gran número de funcionalidades no hace falta segregar interfaces.
Inversión de dependencias: se cumple, ya que ninguna clase de la carpeta application usa directamente el repositorio, sino una interfaz de este.

### PATRONES DE DISEÑO YA APLICADOS EN EL PROGRAMA
Patrón creacional Builder a traves @Builder.
Patrón creacional Singleton a traves de @Scope.
Patrón de comportamiento Command a traves de la carpeta command.

### PRINCIPIOS SOLID APLICADOS POR MI
Single responsibility: las modificaciones realizadas no altera el cumplimiento de este principio.
Open/close: al introducir el nuevo comportamiento sí se cumple el principio abierto/cerrado.
Sustitucion de Liskov: no hay herencia de clases, pero se cumple al poder añadir MyJPARepository sin cambiar el comportamiento de las demás clases.
Como no hay un gran número de funcionalidades no hace falta segregar interfaces
Inversión de dependencias: al aplicar un patrón adapter, se consigue desacoplar las demás capas para que solo dependan del adaptador.

### PATRONES DE DISEÑO APLICADOS POR MI
Patrón creacional Builder a traves @Builder.
Patrón creacional Singleton a traves de @Scope.
Patrón estructural Adapter, es el patrón principal introducido para resolver el problema.