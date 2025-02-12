package com.crioprogram.stayease.repository;

import com.crioprogram.stayease.model.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, Integer> {
}
