package com.crioprogram.stayease.repository;

import com.crioprogram.stayease.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    Hotel findByHotelName(String hotelName);
}
