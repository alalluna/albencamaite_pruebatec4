package com.app.repositories;

import com.app.entities.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelBookingRepositoryInterface extends JpaRepository<HotelBooking, Long> {
}
