package com.train.booking.service;

import java.util.List;

import com.train.booking.dto.ReceiptDto;
import com.train.booking.entity.Bookings;

public interface ReceiptService {

	 public List<ReceiptDto> convertToReceipt(List<Bookings> bookings);

	
}
