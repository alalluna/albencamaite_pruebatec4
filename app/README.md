# 🚀 API REST FOR TRAVEL AGENCY

## 1️⃣ Descripcion general 🐳

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

📂 Archivos

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
    - SecurityConfig.java

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

✅ 
🚦 

## 2️⃣ Explicación de la lógica de negocio

 ### METODOS LIST: listan objetos "habilitados"
 ### METODOS FINDONE: muestran un objeto "habilitados"
 🎉

---
###  titular tres 📄

### 5 subtitulos enumerados con icono herramientas 🛠️

---
###  🏃‍♂️

para resaltar **`algo`**   
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
User Story Nº 1: Obtener un listado de todos los hoteles registrados

Método GET


✅ Path: /agency/hotels

✅ User Story Nº 2: Obtener un listado de todos los habitaciones disponibles en un determinado rango de fechas y según el destino seleccionado.

Método GET


Path: /agency/rooms?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&destination="nombre_destino"

User Story Nº 3: Realizar una reserva de un habitación, indicando cantidad de personas, fecha de entrada, fecha de salida y tipo de habitación. Obtener como respuesta el monto total de la reserva realizada

Método POST


Path: /agency/room-booking/new


⚠️ Se pueden realizar cambios en el JSON en caso de ser necesario, el mismo es solo de referencia.


⚠️ Para ninguna de estas historias de usuario es necesario estar autenticado para acceder a los endpoints ya que son requerimientos que utilizarán los propios clientes de la empresa.


Historias de usuario de Vuelos
✅ User Story Nº 4: Obtener un listado de todos los vuelos registrados

Método GET


✅ Path: /agency/flights

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
User Story Nº 7: Permitir la realización de operaciones de alta, baja y modificación sobre una base de datos tanto para la gestión de vuelos como para la gestión de hoteles a los empleados de la agencia que se encuentren AUTENTICADOS. Las operaciones de lectura deben estar disponibles tanto para empleados autenticados como para público en general (usuarios).


Métodos GET, POST, PUT y DELETE


Paths Hoteles:


POST: /agency/hotels/new
PUT: /agency/hotels/edit/{id}
DELETE: /agency/hotels/delete/{id}
✅ GET: /agency/hotels/{id} → Hotel en particular
✅ GET: /agency/hotels → Todos los hoteles



Paths Vuelos:

POST: /agency/flights/new
PUT: /agency/flights/edit/{id}
DELETE: /agency/flights/delete/{id}
✅ GET: /agency/flights/{id} → Vuelo en particular
✅ GET: /agency/flights → Todos los vuelos


⚠️ Para las bajas se puede optar por borrado físico (eliminación total del registro en la base de datos) o lógico (marcar mediante una bandera lógica el borrado o no de un registro pero que el mismo siga permaneciendo en la base de datos aunque esté oculto para la lógica)



Validaciones necesarias (básicas)


Para bajas y modificaciones debe existir el hotel, reserva, habitación o vuelo correspondiente. Caso contrario, se debe retornar el correspondiente status code y msje.


✅ Para las consultas, en caso de no encontrar resultados se debe informar dicha situación mediante un mensaje.


Para altas, validar que no exista anteriormente una reserva con idénticas características.


Antes de dar de baja un vuelo o un hotel, validar que no se encuentre actualmente en una reserva. En caso de que sea así, no se podrá eliminar el registro, sin antes haber cancelado/eliminado la reserva.


Cualquier validación extra necesaria o complementaria que se considere necesaria puede ser implementada sin problema alguno.

Extra (sugerencias)
A continuación se sugiere una serie de test unitarios a llevar a cabo; sin embargo, en caso de que se considere necesario implementar otros, esto es totalmente viable.
Se solicita al menos la implementación de 1 TEST UNITARIO para manifestar la correcta comprensión de uso de ésta herramienta.
⚠️ Nota: Tener en cuenta que los datos de entrada pueden variar dependiendo del modelado que haya sido realizado por cada desarrollador. En caso de corresponder, realizar las modificaciones/adaptaciones correspondientes necesarias en los tests unitarios sugeridos.


User Story
Situaciones/Datos de entrada
Comportamiento Esperado

1
Se envía solicitud de listado de todos los hoteles registrados.
- Si hay hoteles registrados: Permite continuar con normalidad y muestra listado completo.    -Si no hay hoteles: Notifica la no existencia mediante una excepción.

