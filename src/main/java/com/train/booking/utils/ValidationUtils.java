package com.train.booking.utils;

import org.springframework.stereotype.Component;

import com.train.booking.constants.Constants;

@Component
public class ValidationUtils {
	
	public boolean isValidEmail(String email) {
		return email.matches(Constants.EMAIL_REGEX);
	}

}
