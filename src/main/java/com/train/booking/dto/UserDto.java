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
public class UserDto {
	
	@Valid
	
	@NotNull(message = "First Name is mandatory")
	@NotBlank(message = "First Name is mandatory")
	private String firstName;
	
	@NotNull(message = "Last Name is mandatory")
	@NotBlank(message = "Last Name is mandatory")
	private String lastName;
	
	@NotNull(message = "Email is mandatory")
	@NotBlank(message = "Email is mandatory")
	private String email;

}
