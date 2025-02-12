package com.crioprogram.stayease.controller;

import com.crioprogram.stayease.dto.ResponseDTO;
import com.crioprogram.stayease.dto.RoomBookingDTO;
import com.crioprogram.stayease.service.IRoomBookingService;
import com.crioprogram.stayease.utility.JWTService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/")
public class RoomBookingController {

    @Autowired
    private IRoomBookingService roomBookingService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/hotel-booking")
    public ResponseEntity<ResponseDTO> roomBooking(@RequestBody @Valid RoomBookingDTO roomBookingDTO,
                                                   @RequestHeader("Authorization") String token){
        System.out.println("Authorization : "+ token);
        int userIdFromToken = jwtService.getUserIdFromToken(token);
        System.out.println("User Id from token : "+userIdFromToken);
        roomBookingDTO.setUserId(userIdFromToken);
        ResponseDTO responseDTO = roomBookingService.addRoomBooking(roomBookingDTO);
        return new ResponseEntity<>(responseDTO, responseDTO.getCode());
    }

    @PutMapping("/cancel-booking/{bookingId}")
    public ResponseEntity<ResponseDTO> cancelBooking(@PathVariable int bookingId){
        ResponseDTO responseDTO = roomBookingService.cancelRoomBooking(bookingId);
        return new ResponseEntity<>(responseDTO, responseDTO.getCode());
    }
}
