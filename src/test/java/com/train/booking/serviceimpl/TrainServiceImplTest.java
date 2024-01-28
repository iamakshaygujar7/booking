package com.train.booking.serviceimpl;


import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.train.booking.dto.TrainDto;
import com.train.booking.entity.TrainBook;
import com.train.booking.exception.BookingException;
import com.train.booking.repositories.BookingRepository;
import com.train.booking.repositories.TrainRepository;
import com.train.booking.repositories.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class TrainServiceImplTest {

	@InjectMocks
	private TrainServiceImpl trainServiceImpl;

	@Mock
	private TrainRepository trainRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BookingRepository bookingRepository;

	public static final String TRAIN_NAME = "London Express";
	public static final String FROM = "London";
	public static final String TO = "Paris";
	public static final Double PRICE = 20d;

	@Test
	public void testSaveTrain() {
		var trainDto = new TrainDto();
		trainDto.setTrainName(TRAIN_NAME);
		trainDto.setFromLocation(FROM);
		trainDto.setToLocation(TO);
		trainDto.setPrice(PRICE);

		Mockito.when(trainRepository.findByTrainNameAndActiveIndicator(Mockito.anyString(), Mockito.any()))
				.thenReturn(Optional.empty());
		trainServiceImpl.saveTrain(trainDto);
		Mockito.verify(trainRepository, Mockito.times(1)).save(Mockito.any());
	}
	
	@Test(expected = BookingException.class)
	public void testSaveTrainWithAlreadyPresent() {
		var trainDto = new TrainDto();
		trainDto.setTrainName(TRAIN_NAME);
		trainDto.setFromLocation(FROM);
		trainDto.setToLocation(TO);
		trainDto.setPrice(PRICE);

		Mockito.when(trainRepository.findByTrainNameAndActiveIndicator(Mockito.anyString(), Mockito.any()))
				.thenReturn(Optional.of(new TrainBook()));
		trainServiceImpl.saveTrain(trainDto);
//		Assert.assertThrows(BookingException.class, ()->trainServiceImpl.saveTrain(trainDto));
	}

}
