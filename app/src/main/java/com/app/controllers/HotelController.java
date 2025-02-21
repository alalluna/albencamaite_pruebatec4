package com.app.controllers;

import com.app.dtos.HotelDTO;
import com.app.services.HotelServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/agency")

public class HotelController {
    @Autowired
    HotelServiceInterface service;

    //GET: localhost:8080/agency/hotels → Todos los hoteles
    @GetMapping({"/hotels","/hotels/"})
    public ResponseEntity<List<HotelDTO>> all(){
        List<HotelDTO>  listOfObjects = service.list();
        return ResponseEntity.ok(listOfObjects);
    }

    //GET: localhost:8080/agency/hotels/{id} → Hotel en particular
    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDTO> findById(@PathVariable Long id){
        HotelDTO object = service.findOne(id);
        return ResponseEntity.ok(object);
    }

    //POST: /agency/hotels/new

    //PUT: localhost:8080/agency/hotels/edit/{id}

    // DELETE: localhost:8080/agency/hotels/delete/{id}
}
