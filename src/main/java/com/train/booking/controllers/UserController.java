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
import com.train.booking.dto.ReceiptDto;
import com.train.booking.dto.UserDto;
import com.train.booking.entity.UserBook;
import com.train.booking.exception.BookingException;
import com.train.booking.service.UserService;
import com.train.booking.utils.ValidationUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private ValidationUtils validationUtils;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> addUser(@Valid @RequestBody UserDto userDto){
		UserBook user = new UserBook();
		try {
			if(validationUtils.isValidEmail(userDto.getEmail())) {
				user = userService.saveUser(userDto);
			} else {
				return new ResponseEntity<>(Messages.EMAIl_INCORRECT, HttpStatus.BAD_REQUEST);
			}
			
		} catch(BookingException ex) {
			return new ResponseEntity<>(Messages.USER_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllUsers(){
		List<UserBook> users = new ArrayList<>();
		try {
			users = userService.getAllUsers();
		} catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	} 
	
	@GetMapping("/getUsersSeatBySection")
	public ResponseEntity<?> getSeatsForUsers(@RequestParam Long userId, @RequestParam String section){
		List<ReceiptDto> receipts = new ArrayList<>();
		try {
			receipts = userService.getUsersSeatBySection(userId, section);
		} catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(receipts, HttpStatus.OK);
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
