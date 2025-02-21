package com.app.repositories;

import com.app.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepositoryInterface extends JpaRepository<Flight, Long> {
}
