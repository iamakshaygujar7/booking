package com.train.booking.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ModifyBookingDto {

	@Valid

	@NotNull(message = "User is mandatory")
	@NotBlank(message = "User is mandatory")
	private Long bookingId;
	
	@NotNull(message = "User is mandatory")
	@NotBlank(message = "User is mandatory")
	private Long userId;

	@NotNull(message = "Train is mandatory")
	@NotBlank(message = "Train is mandatory")
	private Long trainId;
	
	@NotNull(message = "Section is mandatory")
	@NotBlank(message = "Section is mandatory")
	private String section;
	
	@NotNull(message = "Price is mandatory")
	private Double price;
	
	@NotNull(message = "Old Seat Number is mandatory")
	private List<Long> oldSeatNumbers;
	
	@NotNull(message = "New Seat Number is mandatory")
	private List<Long> newSeatNumbers;

}
