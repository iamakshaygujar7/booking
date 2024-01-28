package com.train.booking.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.train.booking.constants.Constants;
import com.train.booking.constants.Messages;
import com.train.booking.dto.ReceiptDto;
import com.train.booking.dto.UserDto;
import com.train.booking.entity.BookingDetails;
import com.train.booking.entity.Bookings;
import com.train.booking.entity.UserBook;
import com.train.booking.enums.ActiveIndicator;
import com.train.booking.exception.BookingException;
import com.train.booking.repositories.BookingRepository;
import com.train.booking.repositories.UserRepository;
import com.train.booking.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookingRepository bookingRepository;

	@Override
	public UserBook saveUser(UserDto userDto) throws BookingException {
		Optional<UserBook> userOp = userRepository.findByEmailAddressAndActiveIndicator(userDto.getEmail(),
				ActiveIndicator.A);
		if (userOp.isPresent()) {
			throw new BookingException(Messages.USER_EXISTS);
		}
		UserBook user = new UserBook();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmailAddress(userDto.getEmail());
		user.setCreatedBy(Constants.SYSTEM_USER);
		user.setLastEditedBy(Constants.SYSTEM_USER);
		user.setCreatedDate(new Date());
		user.setLastEditedDate(new Date());
		return userRepository.save(user);
	}

	@Override
	public List<UserBook> getAllUsers() {
		return userRepository.findAllByActiveIndicator(ActiveIndicator.A);
	}

	@Override
	public List<ReceiptDto> getUsersSeatBySection(Long userId, String section) {
		List<Bookings> bookings = bookingRepository.findAllByUserIdAndSectionAndActiveIndicator(userId, section,
				ActiveIndicator.A);
		List<ReceiptDto> receipts = new ArrayList<>();
		for (var booking : bookings) {
			ReceiptDto receipt = new ReceiptDto();
			List<BookingDetails> bookingDetails = booking.getBookingDetails().stream().collect(Collectors.toList());
			List<Long> bookedSeats = bookingDetails.stream()
					.filter(res -> ActiveIndicator.A.equals(res.getActiveIndicator()))
					.map(BookingDetails::getSeatNumber).collect(Collectors.toList());
			Double price = bookingDetails.stream().filter(res -> ActiveIndicator.A.equals(res.getActiveIndicator()))
					.mapToDouble(res -> res.getPrice()).sum();
			receipt.setFirstName(booking.getUserBook().getFirstName());
			receipt.setLastName(booking.getUserBook().getLastName());
			receipt.setTrainName(booking.getTrainBook().getTrainName());
			receipt.setFromLocation(booking.getTrainBook().getFromLocation());
			receipt.setToLocation(booking.getTrainBook().getToLocation());
			receipt.setSection(booking.getSection());
			receipt.setSeatNumbers(bookedSeats);
			receipt.setTotalPrice(price);
			receipt.setCurrencyRef(Constants.CURRENCY);
			receipts.add(receipt);
		}
		return receipts;
	}

}
