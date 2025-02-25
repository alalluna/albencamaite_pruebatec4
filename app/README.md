# 🚀 API REST FOR TRAVEL AGENCY

## DESCRIPCIÓN GENERAL 🐳

Una agencia de turismo desea llevar a cabo el desarrollo de una aplicación que le permita recibir solicitudes de reservas para los diferentes tipos de 
paquetes que ofrece. Por el momento los dos servicios con los que cuenta son el de búsqueda y reserva de hoteles y búsqueda y reserva de vuelos.


🔗 herramientas: Java, Spring Boot, Testing, JPA + Hibernate, Spring Security, lombock , mysql, postman.


🔍 Observaciones: Las operaciones y las relaciones están centradas en la reserva de habitaciones y vuelos que disfrutan x usuarios
  Por lo que aparte de crear usuarios, hoteles y vuelos, debo establecer una lógica relación para las reservas.
  He pensado en una agencia de verdad, como sería su programa, existen claramente dos areas: las reservas de habitaciones y vuelos. 
  Pero para poder crear estas relaciones necesito de otras entidades en este caso Hotel, Flight y User.

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
    Se filtran los elementos habitilados y no reservados(getTrueList)
    Si la lista esta vacia lanza excepcion y el controlador devuelve el ErroDTO(validateNonEmptyList)
    

 ### MÉTODOS FINDONE: muestran un objeto "habilitados" y disponible 
    Si no lanza excepciones : si no existe en bbdd. 
    Si no esta habilitada (validateAvailability).
    O si ya esta reservada (validateNonBooked).
 

### MÉTODOS DELETE: cambiar el boolean Available a false para que quede en la base de datos
    Si no existe el objeto lanza excepción, si existe y ya está inhabilitado tambien.
    No estoy segura pero creo que si no se elimina de verdad no puedo poner @Deletemapping.
    Si esta habilitada (validateAvailability) o si no esta reservada (validateNonBooked) se cambia el boolean Available a False.


### MÉTODOS CREATE: Se validan campos y se guarda un objeto nuevo Flight / Hotel
    Si hay datos null o en blanco , se devuelve una excepcion (validateDTO). 
    Si las fechas no son adecuadas (validateObjectDates).
    Una vez se mapea al objeto se valida que no haya otro igual en la base de datos (validateNonDuplicateHotel).
    Se setea el booked en false, para dejarlo disponible a las reservas y se guarda.


### MÉTODOS UPDATE: Se validan campos y se guarda el objeto editado Flight / Hotel
    Se busca por id. Si esta habilitada (validateAvailability) o si no esta reservada (validateNonBooked) 
    Se añaden los campos con los nuevos datos.
    Se verifica que se incluyen fechas correctas (validateObjectDates) y se guarda.

### MÉTODOS FILTER: Se validan campos y se muestran listas de objetos Flight / Hotel
    Se parsean las fechas para poder operar 

 🎉

---

###  CORRECCIONES NECESARIAS Y SUPUESTOS📄
- Como haré un eliminado lógico, parto de que los datos con los que se pueden trabajar tanto en hoteles como vuelos no son todos validos
  Por lo que a la hora de operar válido que todos los hoteles/vuelos registrados sean "habilitados" y "no reservado").
  De esta manera cumplo con la condición de no operar con hoteles/vuelos ya reservados o ya inhabilitados

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

- Me está dando problemas los campos de tipo fecha, y no quiero transformarlos en el controlador, ni andar formateando tod el rato.
  Por ello he creado una clase que formatea las fechas para utilizarla haya donde me haga falta y he dejado la etiqueta JsonFormat de los dtos.

- He tenido problema en las relaciones, yo pensaba que como había muchos usuarios en cada reserva y un usuario podía tener muchas reservas, 
  Planteado así pensé que la relación debía ser ManyToMany, pero no consigo resolverlo. 
  Por ello que he cambiado la relación a one to many (una reserva puede contener a muchos usuarios).

- En las reservas de hotel mis habitaciones de hotel tienen un rango de fechas de disponibilidad, mi código permite realizar una reserva 
  siempre y cuando las fechas comprendidas en el chekin y chekout, sea inferior o igual a la disponibilidad de la habitación. Si la selección 
  de fechas es exactamente igual, la disponibilidad cubierta es total, pero si el chekin y chekout de la reserva es inferior a la fecha 
  de inicio y fin de la disponibilidad de la habitación esta disponibilidad cambia a "reservado" y quedaría algún día suelto sin huésped.

- No estoy segura de que pasará si realizo datos con palabras españolas(es decir con acentos), no he planteado esa posibilidad, ya que tampoco era un requisito del proyecto, por lo que es importante escribir los datos sin acentos
---
### TEST UNITARIO 🛠️

---




Extra (sugerencias)
A continuación se sugiere una serie de test unitarios a llevar a cabo; sin embargo, en caso de que se considere necesario implementar otros, esto es totalmente viable.
Implementación de 1 TEST UNITARIO 
⚠️ Nota: Tener en cuenta que los datos de entrada pueden variar dependiendo del modelado que haya sido realizado por cada desarrollador. En caso de corresponder, realizar las modificaciones/adaptaciones correspondientes necesarias en los tests unitarios sugeridos.
###  🏃‍♂️

para resaltar
listar
1.
2.
3.
4.
...

```describe un fragmento de un codigo
aqui va el codigo
```
**`REsaltado`**:Mas su explicacion

---

### subtitulo : con icono correr🏃‍♂️

---

¡Felicidades! 🎉ya tienes tu marckdown 🐳🔥

