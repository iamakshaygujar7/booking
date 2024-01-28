package com.train.booking.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.train.booking.constants.Constants;
import com.train.booking.constants.Messages;
import com.train.booking.dto.TrainDto;
import com.train.booking.entity.TrainBook;
import com.train.booking.entity.UserBook;
import com.train.booking.enums.ActiveIndicator;
import com.train.booking.exception.BookingException;
import com.train.booking.repositories.BookingRepository;
import com.train.booking.repositories.TrainRepository;
import com.train.booking.repositories.UserRepository;
import com.train.booking.service.TrainService;

@Service
public class TrainServiceImpl implements TrainService{
	
	@Autowired
	private TrainRepository trainRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookingRepository bookingRepository;

	public TrainBook saveTrain(TrainDto trainDto) throws BookingException{
		Optional<TrainBook> trainOp = trainRepository.findByTrainNameAndActiveIndicator(trainDto.getTrainName(), ActiveIndicator.A);
		if(trainOp.isPresent()) {
			throw new BookingException(Messages.USER_EXISTS);
		}
		TrainBook train = new TrainBook();
		train.setTrainName(trainDto.getTrainName());
		train.setFromLocation(trainDto.getFromLocation());
		train.setToLocation(trainDto.getToLocation());
		train.setPrice(trainDto.getPrice());
		train.setCreatedBy(Constants.SYSTEM_USER);
		train.setLastEditedBy(Constants.SYSTEM_USER);
		train.setCreatedDate(new Date());
		train.setLastEditedDate(new Date());
		return trainRepository.save(train);
	}

	public List<TrainBook> getAllTrains(){
		return trainRepository.findAllByActiveIndicator(ActiveIndicator.A);
		
	}

	@Override
	public void removeUserFromTrain(Long userId, Long trainId) throws BookingException {
		var user = validateUser(userId);
		var train = validateTrain(trainId);
		var bookings = bookingRepository.findAllByTrainBookAndUserIdAndActiveIndicator(trainId, userId,ActiveIndicator.A);
		for(var booking : bookings) {
			booking.setActiveIndicator(ActiveIndicator.D);
		}
		bookingRepository.saveAllAndFlush(bookings);
	}
	
	private UserBook validateUser(Long userId) throws BookingException {
		Optional<UserBook> userOp = userRepository.findById(userId);
		if (userOp.isPresent()) {
			return userOp.get();
		} else {
			throw new BookingException(Messages.USER_NOT_EXISTS);
		}
	}

	private TrainBook validateTrain(Long trainId) throws BookingException {
		Optional<TrainBook> trainOp = trainRepository.findById(trainId);
		if (trainOp.isPresent()) {
			return trainOp.get();
		} else {
			throw new BookingException(Messages.TRAIN_NOT_EXISTS);
		}
	}
}
