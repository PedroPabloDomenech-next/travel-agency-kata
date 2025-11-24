<h3 font-weight = bold> ¿Qué principios SOLID se han utilizado para codificar este proyecto e indica algún ejemplo?</h3>
<p> <b>Sigle reponsability: </b>Sí se cumple ya que cada clase tiene una única resposabilidad.</p>
<p><b> Open/CLosed: </b>Se cumple porque la interfaz CustomerRepository permite amplliar el comportamiento de la parte de persistencia. Sin embargo, en otras clases como CreateCustomerCommand, CustomerController o Customer no existe la posibilidad de ampliar el comportamiento del programa sin modificar el código de estas clases.</p>
<p><b> Liskov principel: </b> No hay herencias por lo que no puede cumplirse </p>
<p><b> Interface segregation: </b>Sí se cumple porque todas las clases que implementan una interfaz implementan todos sus métodos. Por ejemplo, la interfaz CustomerRepository está completamente implementada por CustomerInMemoryRepository.</p>
<p><b> Dependency inversion: </b>Sí se cumple porque las clases que lo necesitan usan la interfaz CustomerRepository en vez de alguna de sus implementaciones. </p>

<h3 font-weight = bold>¿Qué patrones de diseño has observado e indica algún ejemplo?</h3>
<b>Creacionales</b>
<ul>
    <li>Singleton: en la clase CustomerInMemoryRepository con la anotación de Spring @Scope("singleton")</li>
    <li>Builder: todas las clases que contienen la anotación @Builder de Lombok como, por ejemplo, Customer o CustomerEntity</li>
</ul>
<b>Estructurales</b>  
<ul> 
    <li>No he encontrado ningún patrón de este tipo</li>
</ul>
<b> Comportamiento </b>  
<ul> 
    <li>Command: con las clases CreateCustomerCommand y GetCustomerQuery </li>
</ul>


**Analiza el código del proyecto y refactoriza las clases que no cumplan SOLID**
