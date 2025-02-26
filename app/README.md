# 🚀 API REST FOR TRAVEL AGENCY

## DESCRIPCIÓN GENERAL 🐳

Una agencia de turismo desea llevar a cabo el desarrollo de una aplicación que le permita recibir solicitudes de reservas para los diferentes tipos de 
paquetes que ofrece. Por el momento los dos servicios con los que cuenta son el de búsqueda y reserva de habitaciones de hotel y búsqueda y reserva de vuelos.


🔗 herramientas: Java, Spring Boot, Testing, JPA + Hibernate, Spring Security, lombock , mysql, postman.


🔍 Observaciones: Las operaciones y las relaciones están centradas en la reserva de habitaciones y vuelos que disfrutan x usuarios
  Por lo que aparte de crear usuarios, habitaciones de hotel y vuelos, debo establecer una lógica relación para las reservas.
  Pensé en una agencia de verdad, como sería su programa, pero ahora veo que mi api no es tan avanzada como para eso, sin embargo, si cumple con los aspectos fundamentales de la consigna.
  En esta api existen claramente dos áreas: las reservas de habitaciones y vuelos. Pero para poder crear estas relaciones necesito al menos cinco entidades. 
  Al arrancar el programa mysql me crea automáticamente dos tablas mas intermedias que serian los pasajeros y los huéspedes

🖥️ Descarga disponible y control de versiones
https://github.com/alalluna/albencamaite_pruebatec4.git

---

📂 ARCHIVOS

---

## ENTIDADES

    - Hotel.java
    - User.java
    - Flight.java
    - HotelBooking.java
    - FlightBooking.java

## DTOS
    - ErrorDTO.java
    - FlightDTO.java
    - HotelDTO.java
    - HotelBookingDTO.java
    - FlightBookingDTO.java
    - UserDTO.java
    - HotelSummaryDTO.java

## CONFIG
    - SecurityConfig.java 🚦 

## CONTROLLERS
    - HotelController.java
    - FlightController.java

## REPOSITORIES
    - HotelRepositoryInterface
    - FlightRepositoryInterface

## SERVICES
    - DateUtilService
    - FlightService 
    - FlightServiceException
    - FlightServiceInterface
    - FlightServiceValidation
    - HotelService
    - HotelServiceException
    - HotelServiceInterface
    - HotelServiceValidations
---

## LÓGICA DE NEGOCIO

 ### MÉTODOS LIST: listan objetos "habilitados" (Se muestran los habilitados y disponibles para las reservas) 
    Se filtran los habitaciones de hotel y vuelos habilitados y no reservados(getTrueList)
    Si la lista esta vacia lanza excepcion y el controlador devuelve el ErroDTO(validateNonEmptyList)

 ### MÉTODOS FINDONE: muestran un objeto "habilitados" y disponible 
    Como yo me plantee que la logica estaba centrada en habitaciones de hotel y vuelos, esta busqueda representa estos objetos.
    Si la habitacion de hotel/vuelo no existe lanza excepciones : 
    - si no existe en bbdd lanza excepcion, si esta inhabilitado lanza excepcion (validateExist)
    - puedes ver por id si una habitación/vuelo existe con sus datos y si está o no reservado

### MÉTODOS DELETE: cambiar el boolean Available a false para que quede en la base de datos
    Si no existe el la habitacion de hotel/vuelo lanza excepción, si existe y ya está inhabilitado tambien.
    No estoy segura pero creo que si no se elimina de verdad no puedo poner @Deletemapping.
    Si esta habilitada  o si no esta reservada (validateAvailability) se cambia el boolean Available a False.
    Para poder eliminar el hotel habria que eliminar todas sus habitaciones, recorriendo una lista que filtra por nombre y elimina esos objetos.


### MÉTODOS CREATE: Se validan campos y se guarda un objeto nuevo Flight / Hotel
    Si hay datos null o en blanco ,Si las fechas no son adecuadas, se devuelve una excepcion (validateFlightDTODates,validateHotelDTODates).
    Una vez se mapea al objeto se valida que no haya otro igual en la base de datos (validateNonDuplicateHotel).
    los codigos de vuelo y hotel son mas bien codigos de esta habitacion y de ese asiento/s.
    Se setea el booked en false, para dejarlo disponible a las reservas y se guarda.


### MÉTODOS UPDATE: Se validan campos y se guarda el objeto editado Flight / Hotel
    Se busca por id la habitación de hotel o el vuelo. 
    Si el objeto esta habilitado o no reservado (validateAvailability), se añaden los campos con los nuevos datos.
    Se verifica que se incluyen datos correctos correctas (validateFlightDTODates,validateHotelDTODates) y se guarda.

### MÉTODOS FILTER: Se validan campos y se muestran listas de objetos Flight / Hotel
    Se parsean las fechas para poder filtrar los datos LocalDate, ya que vienen en forma de string(DateUtilService.parseDate(...).
    Se filtran las fechas teniendo en cuenta que puedo aprovechar el metodo getTrueList para evitar los no habilitados/reservados.
    Se valida que la lista no venga vacia, reutilizando validateNonEmptyList y se devuelve la lista en DTO de habitaciones o vuelos.
    En el caso de vuelos existen dos filtros planteados en dos if, para viajes de ida y viajes de ida y vuelta.

### MÉTODOS CREATEBOOKING: Se validan campos y se muestran listas de objetos Flight / Hotel
    Se buscan de entre todos los vuelos/habitaciones de hotel disponibles en bbdd, los getTrueList.
    Como solo se puede reservar de algo no reservado no se podrían repetir reservas, porque tendría que cancelarla primero.
    Se filtran por los parametros que vienen en la solicitud post con findAvailableFlight/Hotel cogiendo el primero que cumpla.
    El objeto Flight/hotel resultante de este filtro debera cambiar a "reservado" con setBooked(true)
    Se crea la reserva ( createNewBooking) y la lista ( createUserList) asociadas al objeto.
    Se asigna la lista a la reseva (newBooking.setPassengers(passengers)/.setHosts(hosts));
    Se asigna la reserva a su objeto (.add) y se guarda (.save)
    Y por ultimo se devuelve un dto HotelBookingDTO/FlightBookingDTO con la informacion de su reserva, vuelo y usuarios

Estos métodos crean habitaciones de hotel y vuelos con el nombre y apellidos de los usuarios en cuestión o sin ellos ya que la consigna lo dice
Aunque normalmente las agencias incluyen a nombre de los huéspedes/pasajeros ya que les pedirán la documentación en el hotel/aeropuerto.

**`Realizar una reserva de un vuelo, indicando cantidad de personas, origen, destino y fecha de ida.`**
**`Realizar una reserva de un habitación, indicando cantidad de personas, fecha de entrada, fecha de salida y tipo de habitación. `**

### MÉTODOS CODEGENERATOR: Creación de códigos únicos para objetos Flight / Hotel
    La estructura tiene 3 partes, la primera basada en origen/destino y nombre de hotel/ciudad.
    Un numero obtenido por el tipo de asiento o de habitacion.
    Un numero random

    Ejemplo: Vuelo Barcelona Madrid (asiento : Economy) = BAMI-5845
    De esta manera el hotel nh puede tener dos habitaciones single disponibles en un mismo rango de fecha, pero con diferente codigo.
    En el caso del vuelo el codigo representa mas bien la reserva de vuelo, el mismo vuelo puede contener diferentes codigos que seran los asientos/reservas.

### MÉTODOS DATEUTIL: Para formatear fechas de String-LocalDate y de LocalDate-String
    Aunque utilicé la anotacion jackson format, pensado que sería suficiente porque los dtos se mostraban como String.
    Pero como el formato era dd/MM/yyyyy y al hacer los post me salían como yyyy-mm-dd me resultaba muy confuso y dificil,
    por lo que añadí estos métodos que sirven tanto para hoteles como vuelos.

### VALIDATIONS: Métodos estáticos que reutilizo para validar la lógica.
    Aquí se encuentran la mayoría de las validaciones necesarias, otras las he incluido dentro de los métodos.
    Como por ejemplo si hay valores nulos, están en blanco, si son negativos, listas vacias, reservados o habilitados...

### EXCEPTIONS: Excepciones personalizadas que permiten personalizar los mensajes de error.
    Constan del mensaje en cuestión y el codigo de error. En controller devuelvo un errorDTO en caso de que haya una excepción.
    Hay una excepcion para cada area y una excepcion para las validacione de fechas,a fin de no repetir codigo.

### ÚLTIMOS MÉTODOS: Para manejar mejor el Crud de hotel

    Como yo he hecho un crud de habitaciones de hotel(no de hoteles) malinterpretando la consigna, se me ha ocurrido una idea para adherirme mejor a la consigna.
    He creado un delete que si elimina todas las habitaciones de un hotele especifico, aunque como el hotel en si mismo no existe, existen las habitaciones,
    se filtra por nombre(hotelName) y elimina todas las habitaciones de ese hotel si estan disponibles (getTrueList). También tengo metodo que muestra con un map un HotelSummaryDTO con los hoteles y algunos datos, para poder ver hoteles.
    Create y Update los dejaré en habitacioens, de este modo. Si quieres crear un hotel solo tienes que crear su primera habitacion.
    (faltaría añadir como editar el campo hotelName en una lista de objetos, filtrando primero por el nombre del hotel, aunque no se si me dará tiempo).
 🎉

---

###  CORRECCIONES NECESARIAS Y SUPUESTOS📄

- Como haré un eliminado lógico de las habitaciones de hotel, parto de que los datos con los que se pueden trabajar tanto en Hotel como Flight no son válidos.
  Por lo que a la hora de "eliminar" por id, válido que todos los hoteles/vuelos registrados sean "habilitados" y "no reservado").
  De esta manera cumplo con la condición de no operar con hoteles/vuelos ya reservados o ya inhabilitados.

- Al escoger como nombre de los parámetros IsAvailable, sin saberlo, me ha dado muchos problemas (aunque ayer iba, hoy no). 
  Era un problema de conflictos con Jackson que tiene esa palabra reservada por lo que he renombrado los parámetros isAvailable por available.
  Y por si acaso también isBooked por booked.

- También tuve problemas con ErrorDTO, no lo usaba correctamente, ya que este es el que devuelve el json y creo que ha de estar en el controlador.
  He cambiado los exceptions que tenían varios constructores y replanteado como devolver un errorDTO en controller para no repetir código.
  Ahora mis excepciones personalizadas recogen el error y el controller devuelve datos si no hay excepción y si hay excepción devuelve un ErroDTO.

- Se supone que los precios deben devolverse en forma de string, pero no recuerdo ninguna anotación que lo hiciera string y le hago  
  **`hotel.getPrice().toString(),`**   y no funciona. De momento lo dejaré asi, más tarde ya veo como lo transformo

- Después de crear validaciones y métodos auxiliares para reducir la longitud de los métodos, los he probado y funciona.
  Aunque podría hacer más, veo que hay suficientes para los crud, más tarde tendré que añadir alguno para las reservas.
  paso a crear update donde podré reciclar algunos de estos métodos auxiliares y validaciones.

- Me está dando problemas los campos de tipo fecha, y no quiero transformarlos en el controlador, ni andar formateando todo el rato.
  Por ello he creado una clase que formatea las fechas para utilizarla haya donde me haga falta y he dejado la etiqueta JsonFormat de los dtos.

- He tenido problema en las relaciones, yo pensaba que como había muchos usuarios en cada reserva y un usuario podía tener muchas reservas... 
  Planteado así pensé que la relación debía ser ManyToMany, pero no consigo resolverlo. 
  Por ello que he cambiado la relación a OneToMany (una reserva puede contener a muchos usuarios).
  Además de la relación que ya existía (Un hotel/vuelo puede tener muchas reservas)

- En las reservas de hotel mis habitaciones de hotel tienen un rango de fechas de disponibilidad, mi código permite realizar una reserva 
  siempre y cuando las fechas comprendidas en el chekin y chekout, sea inferior o igual a la disponibilidad de la habitación. Si la selección 
  de fechas es exactamente igual, la disponibilidad cubierta es total, pero si el chekin y chekout de la reserva es inferior a la fecha 
  de inicio y fin de la disponibilidad de la habitación esta disponibilidad cambia a "reservado" y quedaría algún día suelto sin huésped.

- No estoy segura de que pasará si realizo datos con palabras españolas(es decir con acentos), no he planteado esa posibilidad, ya que tampoco era un 
  requisito del proyecto, por lo que es importante escribir los datos sin acentos para las pruebas.

- He creado un generador de códigos para establecer códigos unicos a los hoteles/vuelos. Están basados en sus datos principales (origen, destino, ciudad, nombre), 
  diferencio sutilmente los códigos por tipo de asiento y o habitación y añado un random. Los utilizo al crear y editar objetos, ya que los datos podrían cambiar.

- He renombrado guests a host porque pensaba que me aclaría mejor si distinguía el nombre de la lista en la creación de la reserva y en la construcción de los dtos que retorna,
  pero en realidad me está liando, como es una lista de huéspedes y empecé llamándole hosts, se quedará así en ambas lógicas, que además están relacionadas directamente. 

- Revisando bien la consigna me di cuenta que malinterpreté la instrucción, yo devolvía una lista de habitaciones y una de vuelos.
  Pero el planteamiento dice mostrar una lista de los hoteles registrados, por lo que he añadido un DTO y un método que extrae esta info.
  En el caso del crud hotel, puedo eliminar un hotel entero, pero no es un eliminado lógico es un delete de los elementos de una lista,
  ya que tengo que yo no tengo Hoteles, mi entidad "Hotel" son habitaciones de un Hotel. Como no es una entidad, sino una propiedad de las habitaciones, lo busco por el String hotelName. Para mostrar el hotel en cuestión. 
  Y para create y update lo dejaría solo en añadir habitaciones o editar habitaciones de hotel.

- Después del trabajo realizado me doy cuenta de las carencias que tiene la api, igual que si hago una reserva de hotel de una oferta determinada, la oferta se bloquea y aparece reservada
  lo mismo ocurre en vuelos, con lo que si hago una reserva de un vuelo, puede incluir una cantidad de personas dentro de esa reserva y esa reserva se bloquea, si una aerolinea ofreciera mas vuelos tendrían que crearse nuevos
  porque se pone como "booked = True". 
- 
- Para el futuro puedo plantearme cambiar esto haciendo que cada vuelo tuviera x asientos disponibles y entonces se reservaran los asientos implicaría un generar La entidad Vuelo y asientos y nuevas relaciones, podría mejorar sustancialmente la api.
  Lo mismo podría realizar creando una entidad hotel y una habitaciones, incluyendo nueva estructura y relaciones.
---
### TEST UNITARIO 🛠️
El test unitario muestra una lista de habitaciones de hotel, sus reservas asociadas se han obviado con una lista vacía. 
Si la lista de habitaciones hotel contiene datos, verificamos que cada dato de la lista se corresponda.
Si la lista de habitaciones de hotel está vacía, devuelve un HotelServiceException.

###  🏃‍♂️Y funciona bien
---

🎉 ¡Gracias por todo Hack a boss!  🐳🔥

**`https://github.com/alalluna`**
**`https://www.behance.net/alalluna`** 

