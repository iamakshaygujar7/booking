package com.train.booking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.train.booking.constants.Messages;
import com.train.booking.dto.ReceiptDto;
import com.train.booking.entity.Bookings;
import com.train.booking.service.BookingService;
import com.train.booking.service.ReceiptService;

@RestController
@RequestMapping("/receipt")
public class ReceiptController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private ReceiptService receiptService;

	@GetMapping("/getAllReceipts")
	public ResponseEntity<?> getAllReceipts(@RequestParam Long userId){
		List<ReceiptDto> receipts = new ArrayList<>();
		List<Bookings> bookings = new ArrayList<>();
		try {
			bookings = bookingService.getAllBookingsByUser(userId);
			receipts = receiptService.convertToReceipt(bookings);
		} catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(receipts, HttpStatus.OK);
	} 
	
}
