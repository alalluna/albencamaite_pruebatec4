package com.app.controllers;

import com.app.dtos.ErrorDTO;
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
    @GetMapping({"/flights/","/flights"})
    public ResponseEntity<?> all(){
        try {
            List<FlightDTO> listOfObjects = service.list();
            return ResponseEntity.ok(listOfObjects);
        } catch (FlightServiceException e) {
            return serviceExceptions(e);
        }
    }

    //GET: /agency/flights/{id} → Vuelo en particular
    @GetMapping("/flights/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try{
        FlightDTO object = service.findOne(id);
        return ResponseEntity.ok(object);
        } catch (FlightServiceException e) {
            return serviceExceptions(e);
        }
    }

    //POST: /agency/flights/new


    //PUT: /agency/flights/edit/{id}

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

    private ResponseEntity<ErrorDTO> serviceExceptions (FlightServiceException e){
        return ResponseEntity.status(e.getError().getStatus()).body(e.getError());
    }
}
