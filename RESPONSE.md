<h3 font-weight = bold> ¿Qué principios SOLID se han utilizado para codificar este proyecto e indica algún ejemplo?</h3>
<p> <b>Sigle reponsability: </b>Sí, se cumple ya que cada clase tiene una única resposabilidad, aunque la clase CustomerController puede mejorarse haciendo que otra clase se encargue de llamar a CreateCustomerCommand y GetCustomerQuery.</p>
<p><b> Open/CLosed: </b>Se cumple en la interfaz CustomerRepository que permite ampliar el comportamiento de la parte de persistencia. Sin embargo, en otras clases como CreateCustomerCommand, GetCustomerQuery o Customer no existe la posibilidad de ampliar el comportamiento del programa sin modificar el código de estas clases.</p>
<p><b> Liskov principel: </b> No hay herencias por lo que no puede cumplirse.</p>
<p><b> Interface segregation: </b>Sí, se cumple porque todas las clases que implementan una interfaz implementan todos sus métodos. Por ejemplo, la interfaz CustomerRepository está completamente implementada por CustomerInMemoryRepository.</p>
<p><b> Dependency inversion: </b>Sí, se cumple porque las clases que lo necesitan usan la interfaz CustomerRepository en vez de alguna de sus implementaciones.</p>

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


<h3 font-weight = bold>Analiza el código del proyecto y refactoriza las clases que no cumplan SOLID</h3>
<p>Para mejorar CustomerController he creado una clase adicional CustomerServices que se encarga de comunicarse con CreateCustomerCommand y GetCustomerQuery quitándole la responsabilidad a CustomerController. También he creado un método privado con la conversión de Customer a GetCustomerDTO que se repetía en varios métodos de la clase.<br>
Por otra parte, he creado la interfaz ICommand para que tanto CreateCustomerCommand y GetCustomerQuery la implementen. De esta forma si se quiere añadir más comportamientos u otras formas de implementar este proceso se podría hacer fácilmente a través de la interfaz ICommand. En caso de que a estas dos clases se les añadan nuevos comportamientos diferentes se dividiría esta interfaz en dos para que ninguna de las dos tenga implementaciones vacías de métodos que no necesita.</p>

