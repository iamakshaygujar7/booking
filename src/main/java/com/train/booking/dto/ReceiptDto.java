package com.train.booking.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ReceiptDto {
	
	private String firstName;
	
	private String lastName;
	
	private String trainName;
	
	private String fromLocation;
	
	private String toLocation;
	
	private String section;
	
	private List<Long> seatNumbers;
	
	private Double totalPrice;
	
	private String currencyRef;

}
