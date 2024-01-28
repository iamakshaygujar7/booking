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
import com.train.booking.dto.TrainDto;
import com.train.booking.entity.TrainBook;
import com.train.booking.exception.BookingException;
import com.train.booking.service.TrainService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/train")
public class TrainController {
	
	@Autowired
	private TrainService trainService;

	@PostMapping
	public ResponseEntity<?> addTrain(@Valid @RequestBody TrainDto trainDto) {
		TrainBook train = new TrainBook();
		try {
			train = trainService.saveTrain(trainDto);
		} catch (BookingException ex) {
			return new ResponseEntity<>(Messages.TRAIN_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(train, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		List<TrainBook> trains = new ArrayList<>();
		try {
			trains = trainService.getAllTrains();
		} catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(trains, HttpStatus.OK);
	}
	
	@PostMapping("/remove_user")
	public ResponseEntity<?> removeUserFromTrain(@RequestParam Long userId, @RequestParam Long trainId) {
		TrainBook train = new TrainBook();
		try {
			trainService.removeUserFromTrain(userId, trainId);
		} catch (BookingException ex) {
			return new ResponseEntity<>(Messages.TRAIN_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			return new ResponseEntity<>(Messages.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(train, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
