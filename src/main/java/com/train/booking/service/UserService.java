package com.train.booking.service;

import java.util.List;

import com.train.booking.dto.ReceiptDto;
import com.train.booking.dto.UserDto;
import com.train.booking.entity.UserBook;
import com.train.booking.exception.BookingException;

public interface UserService {

	public UserBook saveUser(UserDto userDto) throws BookingException;
	
	public List<UserBook> getAllUsers();

	public List<ReceiptDto> getUsersSeatBySection(Long userId, String section);
}
