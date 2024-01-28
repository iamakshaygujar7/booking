package com.train.booking.service;

import java.util.List;

import com.train.booking.dto.TrainDto;
import com.train.booking.entity.TrainBook;
import com.train.booking.exception.BookingException;

public interface TrainService {

	public TrainBook saveTrain(TrainDto trainDto) throws BookingException;

	public List<TrainBook> getAllTrains();

	public void removeUserFromTrain(Long userId, Long trainId) throws BookingException;

}
