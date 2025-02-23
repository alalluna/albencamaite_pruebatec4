package com.app.controllers;

import com.app.dtos.ErrorDTO;
import com.app.dtos.HotelBookingDTO;
import com.app.dtos.HotelDTO;
import com.app.services.HotelServiceException;
import com.app.services.HotelServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class HotelController {

    @Autowired
    HotelServiceInterface service;

    //GET: localhost:8080/agency/hotels → Todos los hoteles
    @GetMapping({"/hotels/","/hotels"})
    public ResponseEntity<?> all(){
        try{
            List<HotelDTO>  listOfObjects = service.list();;
            return ResponseEntity.ok(listOfObjects);

        } catch (HotelServiceException e){ return serviceExceptions(e); }
    }

    //GET: localhost:8080/agency/rooms?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&destination="nombre_destino"
    @GetMapping({"/rooms/","/rooms"})
    public ResponseEntity<?> filterList(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam String destination){
        try{
            List<HotelDTO>  listOfObjects = service.filterHotels(dateFrom,dateTo,destination);
            return ResponseEntity.ok(listOfObjects);

        } catch (HotelServiceException e){ return serviceExceptions(e); }
    }

    //GET: localhost:8080/agency/hotels/{id} → Hotel en particular
    @GetMapping("/hotels/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            HotelDTO object = service.findOne(id);
            return ResponseEntity.ok(object);

        } catch (HotelServiceException e){ return serviceExceptions(e); }
    }

    //POST: /agency/hotels/new
    @PostMapping("/hotels/new")
    public ResponseEntity<?> create(@RequestBody HotelDTO hotelDTO){
        try{
            HotelDTO object = service.create(hotelDTO);
            return ResponseEntity.ok(object);
        }catch (HotelServiceException e){ return serviceExceptions(e); }
    }

    //PUT: localhost:8080/agency/hotels/edit/{id}
    @PutMapping("/hotels/edit/{id}")
    public ResponseEntity<?>update(@PathVariable Long id, @RequestBody HotelDTO hotelDTO){
        try{
            HotelDTO object = service.update(id, hotelDTO);
            return ResponseEntity.ok(object);

        }catch (HotelServiceException e){ return serviceExceptions(e); }
    }

    // DELETE: localhost:8080/agency/hotels/delete/{id} (creo que es patch porque no se elimina de verdad)
    @PatchMapping("/hotels/delete/{id}")
    public ResponseEntity<?> noAvailable(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Habitación " + id + " eliminada correctamente.");
        } catch (HotelServiceException e){
            return serviceExceptions(e);
        }
    }

    @PostMapping("/room-booking/new")
    public ResponseEntity<?> create(@RequestBody HotelBookingDTO hotelBookingDTO){
        try{
            HotelBookingDTO object = service.createBooking(hotelBookingDTO);
            return ResponseEntity.ok(object);
        }catch (HotelServiceException e){ return serviceExceptions(e); }
    }


    private ResponseEntity<ErrorDTO> serviceExceptions(HotelServiceException e) {
        return ResponseEntity.status(e.getError().getStatus()).body(e.getError());
    }
}
