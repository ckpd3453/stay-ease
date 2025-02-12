package com.crioprogram.stayease.service;

import com.crioprogram.stayease.dto.HotelDTO;
import com.crioprogram.stayease.dto.ResponseDTO;
import com.crioprogram.stayease.model.Hotel;
import com.crioprogram.stayease.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements IHotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public ResponseDTO addHotel(HotelDTO hotelDTO) {
        try {
            Hotel hotel = new Hotel(hotelDTO.getHotelName(), hotelDTO.getLocation(), hotelDTO.getDescription(),
                    hotelDTO.getAvailableRooms());
            Hotel savedHotel = hotelRepository.save(hotel);
            return new ResponseDTO(HttpStatus.CREATED, savedHotel, "Hotel Added Successfully");
        }
        catch (Exception exception){
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), "Server Error");
        }
    }

    @Override
    public ResponseDTO updateHotelDetail(int hotelId, HotelDTO hotelDTO) {
        try {
            Optional<Hotel> hotels = hotelRepository.findById(hotelId).map((hotel)-> {
                if(hotelDTO.getHotelName() != null) hotel.setHotelName(hotelDTO.getHotelName());
                if(hotelDTO.getLocation() != null) hotel.setLocation(hotelDTO.getLocation());
                if(hotelDTO.getDescription() != null) hotel.setDescription(hotelDTO.getDescription());
                if(hotelDTO.getAvailableRooms() >= 0) hotel.setAvailableRooms(hotelDTO.getAvailableRooms());
                hotelRepository.save(hotel);
                return hotel;
            });
            if(hotels.isEmpty()){
                return new ResponseDTO(HttpStatus.BAD_REQUEST, hotels, "Hotel with this id doesn't exist");
            }
            return new ResponseDTO(HttpStatus.OK, hotels, "Hotel Updated Successfully");
        }
        catch (Exception exception){
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), "Server Error");
        }
    }

    @Override
    public ResponseDTO getAllHotel() {
        try {
            List<Hotel> hotelList = hotelRepository.findAll();
            return new ResponseDTO(HttpStatus.OK, hotelList, "All hotel list fetched successfully");
        }
        catch (Exception exception){
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), "Server Error");
        }
    }

    @Override
    public ResponseDTO getHotelById(int hotelId) {
        try {
            Optional<Hotel> hotels = hotelRepository.findById(hotelId);
            return hotels.map(hotel -> new ResponseDTO(HttpStatus.OK, hotel, "Hotel fetched Successfully"))
                    .orElseGet(() -> new ResponseDTO(HttpStatus.BAD_REQUEST, hotels,
                            "Hotel with this id doesn't exist"));

        }
        catch (Exception exception){
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), "Server Error");
        }
    }

    @Override
    public ResponseDTO removeHotel(int hotelId) {
        try {
            Optional<Hotel> hotels = hotelRepository.findById(hotelId);
            if(hotels.isEmpty()){
                return new ResponseDTO(HttpStatus.BAD_REQUEST, hotels, "Hotel with this id doesn't exist");
            }
            hotelRepository.delete(hotels.get());
            return new ResponseDTO(HttpStatus.OK, null, "Hotel Deleted Successfully");
        }
        catch (Exception exception){
            return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, exception.getLocalizedMessage(), "Server Error");
        }
    }
}
