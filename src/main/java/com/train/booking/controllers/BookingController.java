package com.train.booking.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.train.booking.constants.Messages;
import com.train.booking.dto.BookingsDto;
import com.train.booking.dto.ModifyBookingDto;
import com.train.booking.entity.Bookings;
import com.train.booking.exception.BookingException;
import com.train.booking.service.BookingService;

@RestController
@RequestMapping("/book")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping
	public ResponseEntity<?> bookTrain(@RequestBody BookingsDto bookingsDto){
		Bookings bookings = new Bookings();
		try {
			bookings = bookingService.saveBooking(bookingsDto);
		} catch (BookingException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(bookings, HttpStatus.OK);
	}
	
	@GetMapping("/getUserBookings")
	public ResponseEntity<?> getAllUserBookings(@RequestParam Long userId){
		List<Bookings> bookings = new ArrayList<>();
		try {
			bookings = bookingService.getAllBookingsByUser(userId);
		} catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(bookings, HttpStatus.OK);
	} 
	
	@PostMapping("/modifyBooking")
	public ResponseEntity<?> modifyBooking(@RequestBody ModifyBookingDto bookingsDto){
		Bookings bookings = new Bookings();
		try {
			bookings = bookingService.modifyBooking(bookingsDto);
		} catch (BookingException ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(bookings, HttpStatus.OK);
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
