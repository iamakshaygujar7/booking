package com.train.booking.dto;

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
public class TrainDto {
	
	@Valid

	@NotNull(message = "Name is mandatory")
	@NotBlank(message = "Name is mandatory")
	private String trainName;
	
	@NotNull(message = "From location is mandatory")
	@NotBlank(message = "From Location is mandatory")
	private String fromLocation;

	@NotNull(message = "To Location is mandatory")
	@NotBlank(message = "To Location is mandatory")
	private String toLocation;

	@NotNull(message = "Price is mandatory")
	private Double price;

}
