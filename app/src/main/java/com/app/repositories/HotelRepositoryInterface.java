package com.app.repositories;

import com.app.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepositoryInterface extends JpaRepository<Hotel, Long>{
}
