package com.train.booking.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.train.booking.constants.Constants;
import com.train.booking.constants.Messages;
import com.train.booking.dto.BookingsDto;
import com.train.booking.dto.ModifyBookingDto;
import com.train.booking.entity.BookingDetails;
import com.train.booking.entity.Bookings;
import com.train.booking.entity.TrainBook;
import com.train.booking.entity.UserBook;
import com.train.booking.enums.ActiveIndicator;
import com.train.booking.exception.BookingException;
import com.train.booking.repositories.BookingDetailsRepository;
import com.train.booking.repositories.BookingRepository;
import com.train.booking.repositories.TrainRepository;
import com.train.booking.repositories.UserRepository;
import com.train.booking.service.BookingService;

@Component
public class BookingServiceImpl implements BookingService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TrainRepository trainRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private BookingDetailsRepository bookingDetailsRepository;

	public synchronized Bookings saveBooking(BookingsDto bookingsDto) throws BookingException {
		var user = validateUser(bookingsDto.getUserId());
		var train = validateTrain(bookingsDto.getTrainId());
		validateSeat(bookingsDto);
		List<BookingDetails> bookingDetails = new ArrayList<>();
		Bookings booking = new Bookings();
		booking.setUserBook(user);
		booking.setTrainBook(train);
		booking.setSection(bookingsDto.getSection());
		booking.setBookingDetails(bookingDetails);
		booking.setCreatedBy(Constants.SYSTEM_USER);
		booking.setLastEditedBy(Constants.SYSTEM_USER);
		booking.setCreatedDate(new Date());
		booking.setLastEditedDate(new Date());
		var bookingSaved = bookingRepository.saveAndFlush(booking);
		for (var seats : bookingsDto.getSeatNumbers()) {
			BookingDetails details = new BookingDetails();
			details.setPrice(bookingsDto.getPrice());
			details.setSeatNumber(seats);
			details.setCurrencyRef(Constants.CURRENCY);
			details.setBookings(bookingSaved);
			bookingDetails.add(details);
		}
		if (!bookingDetails.isEmpty()) {
			bookingDetails = bookingDetailsRepository.saveAllAndFlush(bookingDetails);
		}
		return bookingSaved;
	}

	public List<Bookings> getAllBookingsByUser(Long userId) {
		return bookingRepository.findAllByUserIdAndActiveIndicator(userId, ActiveIndicator.A);
	}

	@Override
	public synchronized Bookings modifyBooking(ModifyBookingDto bookingsDto) throws BookingException {
		var user = validateUser(bookingsDto.getUserId());
		var train = validateTrain(bookingsDto.getTrainId());
		validateSeat(bookingsDto);
		List<BookingDetails> bookingDetails = new ArrayList<>();
		List<BookingDetails> removebookingDetails = new ArrayList<>();
		Optional<Bookings> bookingOp = bookingRepository.findById(bookingsDto.getBookingId());
		Bookings booking = bookingOp.get();
		booking.setCreatedBy(Constants.SYSTEM_USER);
		booking.setLastEditedBy(Constants.SYSTEM_USER);
		booking.setCreatedDate(new Date());
		booking.setLastEditedDate(new Date());
		var bookingSaved = bookingRepository.saveAndFlush(booking);

		Map<Long, BookingDetails> detailsMap = booking.getBookingDetails().stream()
				.collect(Collectors.toMap(BookingDetails::getSeatNumber, res -> res));
		for (var seats : bookingsDto.getOldSeatNumbers()) {
			if (detailsMap.containsKey(seats) && detailsMap.get(seats) != null) {
				BookingDetails details = detailsMap.get(seats);
				details.setActiveIndicator(ActiveIndicator.D);
				removebookingDetails.add(details);
			}
		}
		for (var seats : bookingsDto.getNewSeatNumbers()) {
			BookingDetails details = new BookingDetails();
			details.setPrice(bookingsDto.getPrice());
			details.setSeatNumber(seats);
			details.setCurrencyRef(Constants.CURRENCY);
			details.setBookings(bookingSaved);
			bookingDetails.add(details);
		}
		if (!removebookingDetails.isEmpty()) {
			bookingDetailsRepository.saveAllAndFlush(removebookingDetails);
		}
		if (!bookingDetails.isEmpty()) {
			bookingDetails = bookingDetailsRepository.saveAllAndFlush(bookingDetails);
		}
		return bookingSaved;
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

	private void validateSeat(BookingsDto bookingsDto) throws BookingException {
		List<Bookings> bookings = bookingRepository.findAllByTrainBookAndSectionAndActiveIndicator(
				bookingsDto.getTrainId(), bookingsDto.getSection(), ActiveIndicator.A);
		var bookingDetails = bookings.stream().map(Bookings::getBookingDetails).flatMap(List::stream)
				.collect(Collectors.toList());
		if (!bookingDetails.isEmpty()) {
			if (bookingDetails.size() >= Constants.MAX_BOOKINGS) {
				throw new BookingException(Messages.SEATS_FULL);
			}
			Set<Long> alreadyBooked = bookingDetails.stream()
					.filter(res -> ActiveIndicator.A.equals(res.getActiveIndicator()))
					.map(BookingDetails::getSeatNumber).collect(Collectors.toSet());
			alreadyBooked.retainAll(bookingsDto.getSeatNumbers());
			if (!alreadyBooked.isEmpty()) {
				throw new BookingException(Messages.BOOKING_EXISTS);
			}
		}
	}

	private void validateSeat(ModifyBookingDto bookingsDto) throws BookingException {
		List<Bookings> bookings = bookingRepository.findAllByTrainBookAndSectionAndActiveIndicator(
				bookingsDto.getTrainId(), bookingsDto.getSection(), ActiveIndicator.A);
		var bookingDetails = bookings.stream().map(Bookings::getBookingDetails).flatMap(List::stream)
				.collect(Collectors.toList());
		if (!bookingDetails.isEmpty()) {
			if (bookingDetails.size() >= Constants.MAX_BOOKINGS) {
				throw new BookingException(Messages.SEATS_FULL);
			}
			Set<Long> alreadyBooked = bookingDetails.stream()
					.filter(res -> ActiveIndicator.A.equals(res.getActiveIndicator()))
					.map(BookingDetails::getSeatNumber).collect(Collectors.toSet());
			alreadyBooked.retainAll(bookingsDto.getNewSeatNumbers());
			if (!alreadyBooked.isEmpty()) {
				throw new BookingException(Messages.BOOKING_EXISTS);
			}
		}
	}
}
