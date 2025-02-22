# 🚀 API REST FOR TRAVEL AGENCY

## DESCRIPCION GENERAL 🐳

Una agencia de turismo desea llevar a cabo el desarrollo de una aplicación que le permita recibir solicitudes de reservas para los diferentes tipos de 
paquetes que ofrece. Por el momento los dos servicios con los que cuenta son el de búsqueda y reserva de hoteles y búsqueda y reserva de vuelos.


🔗 herramientas: Java, Spring Boot, Testing, JPA + Hibernate, Spring Security, lombock , mysql, postman.


🔍 Observaciones: Las operaciones y las relaciones estan centradas en la reserva de habitaciones y vuelos que disfrutan x usuarios
  Por lo que a parte de crear usuarios, hoteles y vuelos, debo establecer una logica relacion para las reservas.
  He pensando en una agencia de verdad, como sería su programa, existen claramente dos areas: las reservas de habitaciones y vuelos. 
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

## CONFIG
    - SecurityConfig.java 🚦 

## CONTROLLERS
    - HotelController.java
    - FlightController.java

## REPOSITORIES
    - HotelRepositoryInterface
    - FlightRepositoryInterface

## SERVICES
    - HotelServiceInterface
    - HotelService (parto de que aqui deben de verse todos los hoteles registrados "habilitados", es decir no ocultos)
    - HotelServiceException
    - FlightServiceInterface
    - FlightService (hago lo mismo con vuelos)
    - FlightServiceException
---

## lÓGICA DE NEGOCIO

 ### METODOS LIST: listan objetos "habilitados" (Se muestran los habilitados y disponibles para las reservas) 
    Si la lista esta vacia lanza excepcion y el controlador devuelve el ErroDTO

 ### METODOS FINDONE: muestran un objeto "habilitados" y disponible 
    Si no lanza excepciones : si no existe en bbdd, si no esta habilitada o si ya esta reservada

### METODOS DELETE: cambiara el boleano Available a false para que quede en la base de datos
    Si no existe el objeto lanza excepción, si existe y ya está inhabilitado tambien
    No estoy segura pero creo que si no se elimina de verdad no puedo poner @Deletemapping

### METODOS CREATE: Se validan campos y se guarda un objeto nuevo Flight o Hotel
    si hay datos null o en blanco , se devuelve una excepcion, 
    si las fechas no son adecuadas( son pasadas o la vuelta es pasada a la ida) tambien.
    una vez se mapea al objeto se valida que no haya otro igual en la base de datos 
    se setea el booked en false, para dejarlo disponible a las reservas.
 🎉

---

###  CORRECCIONES NECESARIAS📄

- Al escoger como nombre de los parametros IsAvailable , sin saberlo, me ha dado muchos problemas (aunque ayer iba, hoy no). 
  Era un problema de conflictos con jacson que tiene esa palabra reservada por lo que he renombrado los parametros isAvailable por available.
  Y por si acaso tambien isBooked por booked.

- Tambien tuve problemas con ErrorDTO, no lo usaba correctamente , ya que este es el que devuelve el json y creO que ha de estar en el controllador.
  He cambiado los exceptions que tenian varios constructores y replanteado como devolver un errorDTO en controller para no repetir codigo.
  Ahora mis exceciones personalizadas recogen el error y el controller devuelve datos si no hay excepcion y si hay excepcion devuelve un ErroDTO.

- Se supone que los precios deben devolverse en forma de string pero no recuerdo ninguna anotacion que lo hicera string y le hago  
  **`hotel.getPrice().toString(),`**   y no funciona. De momento lo dejaré asi, mas tarde ya veo como lo transformo

- Despues de crear validadiones y metodos auxiliares para reducir la logitud de los metodos, los he probado y funciona.
  Aunque podría hacer mas, veo que hay suficientes para los crud, mas tarde tendre que añadir alguno mas para las reservas.
  paso crear update donde podré reciclar algunos de estos metodos auxiliares y validaciones.


---
### TEST UNITARIO 🛠️

---
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

Historias de usuario de Hoteles y habitaciones

User Story Nº 2: Obtener un listado de todos los habitaciones disponibles en un determinado rango de fechas y según el destino seleccionado.

Método GET


Path: /agency/rooms?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&destination="nombre_destino"

User Story Nº 3: Realizar una reserva de un habitación, indicando cantidad de personas, fecha de entrada, fecha de salida y tipo de habitación. Obtener como respuesta el monto total de la reserva realizada

Método POST

Path: /agency/room-booking/new


⚠️ Se pueden realizar cambios en el JSON en caso de ser necesario, el mismo es solo de referencia.


⚠️ Para ninguna de estas historias de usuario es necesario estar autenticado para acceder a los endpoints ya que son requerimientos que utilizarán los propios clientes de la empresa.


Historias de usuario de Vuelos
✅ User Story Nº 4: Obtener un listado de todos los vuelos registrados

User Story Nº 5: Obtener un listado de todos los vuelos disponibles para una fecha de ida y su correspondiente fecha de vuelta, según el destino y el origen seleccionados (mostrar tanto los de ida como los de vuelta).

Método GET


Path: /agency/flights?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&origin="ciudad1"&destination="ciudad2"

User Story Nº 6: Realizar una reserva de un vuelo, indicando cantidad de personas, origen, destino y fecha de ida. Obtener como respuesta el monto total de la reserva realizada.

Método POST
Path: /agency/flight-booking/new


⚠️ Recordar que solo se especifica fecha de ida, en caso de que sea ida y vuelta deben ser dos vuelos separados y se invierte el orden de origen y destino. Esto lo hace el usuario que compra. No es necesario llevarlo a cabo mediante código.

⚠️ Se pueden realizar cambios en el JSON en caso de ser necesario, el mismo es solo de referencia.


⚠️ Para ninguna de estas historias de usuario es necesario estar autenticado para acceder a los endpoints ya que son requerimientos que utilizarán los propios clientes de la empresa.


Historias de Usuario generales
User Story Nº 7: Permitir la realización de operaciones modificación 

Paths Hoteles:
PUT: /agency/hotels/edit/{id}

Paths Vuelos:
PUT: /agency/flights/edit/{id}

Validaciones necesarias (básicas)


En modificaciones debe existir el hotel, reserva, habitación o vuelo correspondiente. 
Caso contrario, se debe retornar el correspondiente status code y msje.

Cualquier validación extra necesaria o complementaria que se considere necesaria puede ser implementada sin problema alguno.

Extra (sugerencias)
A continuación se sugiere una serie de test unitarios a llevar a cabo; sin embargo, en caso de que se considere necesario implementar otros, esto es totalmente viable.
Implementación de 1 TEST UNITARIO 
⚠️ Nota: Tener en cuenta que los datos de entrada pueden variar dependiendo del modelado que haya sido realizado por cada desarrollador. En caso de corresponder, realizar las modificaciones/adaptaciones correspondientes necesarias en los tests unitarios sugeridos.

Se envía solicitud de listado de todos los hoteles registrados.
- Si hay hoteles registrados: Permite continuar con normalidad y muestra listado completo.    
- Si no hay hoteles: Notifica la no existencia mediante una excepción.

