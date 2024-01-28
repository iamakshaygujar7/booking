package com.train.booking.service;

import java.util.List;

import com.train.booking.dto.BookingsDto;
import com.train.booking.dto.ModifyBookingDto;
import com.train.booking.entity.Bookings;
import com.train.booking.exception.BookingException;

public interface BookingService {

	public Bookings saveBooking(BookingsDto bookingsDto) throws BookingException;

	public List<Bookings> getAllBookingsByUser(Long userId);
	
	public Bookings modifyBooking(ModifyBookingDto bookingsDto) throws BookingException;
}