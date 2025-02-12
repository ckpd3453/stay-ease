package com.crioprogram.stayease.service;

import com.crioprogram.stayease.dto.HotelDTO;
import com.crioprogram.stayease.dto.ResponseDTO;

import java.util.List;

public interface IHotelService {

    ResponseDTO addHotel(HotelDTO hotelDTO);
    ResponseDTO updateHotelDetail(int hotelId, HotelDTO hotelDTO);
    ResponseDTO getAllHotel();
    ResponseDTO getHotelById(int hotelId);
    ResponseDTO removeHotel(int hotelId);
}
