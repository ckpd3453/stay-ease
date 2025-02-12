package com.crioprogram.stayease.service;

import com.crioprogram.stayease.dto.ResponseDTO;
import com.crioprogram.stayease.dto.RoomBookingDTO;
import com.crioprogram.stayease.model.Hotel;
import com.crioprogram.stayease.model.RoomBooking;
import com.crioprogram.stayease.repository.HotelRepository;
import com.crioprogram.stayease.repository.RoomBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomBookingServiceImpl implements IRoomBookingService{

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomBookingRepository roomBookingRepository;


    @Override
    public ResponseDTO addRoomBooking(RoomBookingDTO roomBookingDTO) {
        try{
            Hotel hotel = hotelRepository.findByHotelName(roomBookingDTO.getHotelName());
            if(hotel.getAvailableRooms() == 0) new ResponseDTO(HttpStatus.CONFLICT, null,
                    "Rooms are not available for booking");

            if(roomBookingDTO.getCheckOutDate().isBefore(roomBookingDTO.getCheckInDate())) return new ResponseDTO(HttpStatus.BAD_REQUEST, null, "check-out date should be after check-in date");
            RoomBooking confirmed = new RoomBooking(roomBookingDTO.getUserId(), hotel, roomBookingDTO.getNumberOfGuests(),
                    "CONFIRMED", roomBookingDTO.getCheckInDate(), roomBookingDTO.getCheckOutDate());
            hotel.setAvailableRooms(hotel.getAvailableRooms()-1);
            hotel.setBookings(confirmed);
            hotelRepository.save(hotel);
            RoomBooking roomBooking = roomBookingRepository.save(confirmed);
            return new ResponseDTO(HttpStatus.CREATED, roomBooking, "Hotel Room Booked Successfully.");
        }
        catch (Exception exception){
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(),
                    "Something went wrong");
        }
    }

    @Override
    public ResponseDTO cancelRoomBooking(int bookingId) {
        try{
            Optional<RoomBooking> roomBooking = roomBookingRepository.findById(bookingId);

            if(roomBooking.isEmpty())
                return new ResponseDTO(HttpStatus.BAD_REQUEST, roomBooking, "Room Booking with this id is not available");

            if(roomBooking.get().getStatus().equals("CANCELED"))
                return new ResponseDTO(HttpStatus.BAD_REQUEST, null, "Booking is already canceled for this ID: " + bookingId);;


            roomBooking.get().setStatus("CANCELED");
            roomBookingRepository.save(roomBooking.get());
            Hotel hotel = roomBooking.get().getHotel();
            hotel.setAvailableRooms(hotel.getAvailableRooms() + 1);
            hotelRepository.save(hotel);
            return new ResponseDTO(HttpStatus.ACCEPTED, roomBooking.get(), "Room Booking canceled successfully");
        }
        catch (Exception exception){
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "", "Something went wrong");
        }
    }
}
