package com.train.booking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.train.booking.entity.BookingDetails;
import com.train.booking.enums.ActiveIndicator;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long> {

	@Query("FROM BookingDetails where bookings.bookingId= :bookingId and activeIndicator=:activeIndicator")
	List<BookingDetails> findAllByBookingIdAndActiveIndicator(Long bookingId, ActiveIndicator activeIndicator);

}
