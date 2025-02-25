package com.app.controllers;

import com.app.dtos.ErrorDTO;
import com.app.dtos.FlightBookingDTO;
import com.app.dtos.FlightDTO;
import com.app.services.FlightServiceException;
import com.app.services.FlightServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/agency")
public class FlightController {

    @Autowired
    FlightServiceInterface service;

    //GET: /agency/flights → Todos los vuelos
    //    /agency/flights?dateFrom=dd/mm/aaaa&dateTo=dd/mm/aaaa&origin="ciudad1"&destination="ciudad2"
    @GetMapping({"/flights/", "/flights"})
    public ResponseEntity<?> showFlights (@RequestParam(required = false) String dateFrom,
                                 @RequestParam(required = false) String dateTo,
                                 @RequestParam(required = false) String origin,
                                 @RequestParam(required = false) String destination) {
        try {
            if (dateFrom != null && origin != null && destination != null) {
                List<FlightDTO> listOfObjects = service.filterFlights(dateFrom, dateTo, origin, destination);
                return ResponseEntity.ok(listOfObjects);
            } else {
                List<FlightDTO> listOfObjects = service.list();
                return ResponseEntity.ok(listOfObjects);
            }
        } catch (FlightServiceException e) { return serviceExceptions(e); }
    }

    //GET: /agency/flights/{id} → Vuelo en particular
    @GetMapping("/flights/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
            FlightDTO object = service.findOne(id);
            return ResponseEntity.ok(object);

        } catch (FlightServiceException e) { return serviceExceptions(e); }
    }

    //POST: /agency/flights/new
    @PostMapping("/flights/new")
    public ResponseEntity<?> create(@RequestBody FlightDTO flightDTO){
        try{
            FlightDTO object = service.create(flightDTO);
            return ResponseEntity.ok(object);

        }catch (FlightServiceException e){ return serviceExceptions(e); }
    }

    //PUT: /agency/flights/edit/{id}
    @PutMapping("/flights/edit/{id}")
    public ResponseEntity<?>update(@PathVariable Long id, @RequestBody FlightDTO flightDTO){
        try{
            FlightDTO object = service.update(id, flightDTO);
            return ResponseEntity.ok(object);

        }catch (FlightServiceException e){ return serviceExceptions(e); }
    }

    //DELETE: /agency/flights/delete/{id}
    @PatchMapping("/flights/delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Vuelo " + id + " eliminado correctamente.");

        } catch (FlightServiceException e) {
            return serviceExceptions(e);
        }
    }

    //Post: /agency/flight-booking/new
    @PostMapping("flight-booking/new")
     public ResponseEntity<?> create(@RequestBody FlightBookingDTO flightBookingDTO){
            try{
               FlightBookingDTO object = service.createBooking(flightBookingDTO);
                return ResponseEntity.ok(object);

            }catch (FlightServiceException e){ return serviceExceptions(e); }
        }

    private ResponseEntity<ErrorDTO> serviceExceptions (FlightServiceException e){
        return ResponseEntity.status(e.getError().getStatus()).body(e.getError());
    }
}
