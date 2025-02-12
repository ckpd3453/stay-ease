package com.crioprogram.stayease.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingId;
    private int userId;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)  // Foreign key to Hotel table
    private Hotel hotel;

    @NotNull(message = "Number of guests is required")
    @Min(value = 1, message = "Number of guests must be at least 1")
    @Max(value = 2, message = "Number of guests cannot be more than 2")
    private int numberOfGuests;

    @Pattern(
            regexp = "^(CONFIRMED|CANCELED)$",
            message = "Status must be either CONFIRMED or CANCELED"
    )
    private String status;

    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;

    @FutureOrPresent(message = "Check-out date must be today or in the future")
    private LocalDate checkOutDate;

    public RoomBooking(){}

    public RoomBooking(int userId, Hotel hotel, int numberOfGuests, String status, LocalDate checkInDate, LocalDate checkOutDate) {
        this.userId = userId;
        this.hotel = hotel;
        this.numberOfGuests = numberOfGuests;
        this.status = status;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
