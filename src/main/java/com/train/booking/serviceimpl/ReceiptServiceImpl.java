package com.train.booking.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.train.booking.constants.Constants;
import com.train.booking.dto.ReceiptDto;
import com.train.booking.entity.BookingDetails;
import com.train.booking.entity.Bookings;
import com.train.booking.enums.ActiveIndicator;
import com.train.booking.service.ReceiptService;

@Service
public class ReceiptServiceImpl implements ReceiptService {

	@Override
	public List<ReceiptDto> convertToReceipt(List<Bookings> bookings) {
		List<ReceiptDto> receipts = new ArrayList<>();
		for (var booking : bookings) {
			ReceiptDto receipt = new ReceiptDto();
			List<BookingDetails> bookingDetails = booking.getBookingDetails().stream()
					.collect(Collectors.toList());
			List<Long> bookedSeats = bookingDetails.stream()
					.filter(res -> ActiveIndicator.A.equals(res.getActiveIndicator()))
					.map(BookingDetails::getSeatNumber).collect(Collectors.toList());
			Double price = bookingDetails.stream().filter(res -> ActiveIndicator.A.equals(res.getActiveIndicator()))
					.mapToDouble(res -> res.getPrice()).sum();
			receipt.setFirstName(booking.getUserBook().getFirstName());
			receipt.setLastName(booking.getUserBook().getLastName());
			receipt.setTrainName(booking.getTrainBook().getTrainName());
			receipt.setFromLocation(booking.getTrainBook().getFromLocation());
			receipt.setToLocation(booking.getTrainBook().getToLocation());
			receipt.setSection(booking.getSection());
			receipt.setSeatNumbers(bookedSeats);
			receipt.setTotalPrice(price);
			receipt.setCurrencyRef(Constants.CURRENCY);
			receipts.add(receipt);
		}
		return receipts;
	}

}
