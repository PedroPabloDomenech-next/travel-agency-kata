En la tabla V1_generate_V_0_0_0.sql se ha removido los modificadores NOT NULL de las tablas 
ENROLLMENT_DATE Y ACTIVE, debido a los problemas que causaban.
En CustomerEntity añadidas las Tags @NoArgsConstructor @AllArgsConstructor
Creado el findByPassportNumber en CustomersJPARepository
Creado en /repository el Adapter CustomerRepositoryAdapter
En CustomersInMemoryRepository añadida la tag @Profile("en memoria") para evitar conflictos con el Adapter

En CreateCustomerCommand observamos un patrón Builder.
public String handle() {
Customer customer = Customer.builder()
.id(id)
.name(name)
.surnames(surnames)
.birthDate(birthDate)
.passportNumber(passportNumber)
.build();
customersRepository.saveCustomer(customer);
return customer.getId();
}
En /port la interfaz CustomersRepository cumple Single Responsability(es un repositorio, no hace nada más)
y Open Close, ya que para añadir por ejemplo getByName NO hace falta modificar , 
solo extender(crear nueva funcion)
public interface CustomersRepository {
void saveCustomer(Customer customer);
Optional<Customer> getCustomerById(String id);
Optional<Customer> getCustomerByPassport(String id);
}

En /repository, CustomersInMemoryRepository y CustomersRepositoryAdpter cumplen  Liskov Substitution