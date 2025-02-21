package com.app.controllers;

import com.app.dtos.FlightDTO;
import com.app.services.FlightServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/agency")
public class FlightController {

    @Autowired
    FlightServiceInterface service;

    //GET: /agency/flights → Todos los vuelos
    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> all(){
        List<FlightDTO>  listOfObjects = service.list();
        return ResponseEntity.ok(listOfObjects);
    }

    //GET: /agency/flights/{id} → Vuelo en particular
        @GetMapping("/flights/{id}")
            public ResponseEntity<FlightDTO> findById(@PathVariable Long id){
                FlightDTO object = service.findOne(id);
                return ResponseEntity.ok(object);
            }
    //POST: /agency/flights/new
    //PUT: /agency/flights/edit/{id}
    //DELETE: /agency/flights/delete/{id}



}
