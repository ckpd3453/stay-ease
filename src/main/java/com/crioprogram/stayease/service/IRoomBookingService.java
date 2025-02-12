package com.crioprogram.stayease.service;

import com.crioprogram.stayease.dto.ResponseDTO;
import com.crioprogram.stayease.dto.RoomBookingDTO;

public interface IRoomBookingService {

    public ResponseDTO addRoomBooking(RoomBookingDTO roomBookingDTO);
    public ResponseDTO cancelRoomBooking(int bookingId);
}
