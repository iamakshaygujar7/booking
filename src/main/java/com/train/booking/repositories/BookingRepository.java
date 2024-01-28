package com.train.booking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.train.booking.entity.Bookings;
import com.train.booking.enums.ActiveIndicator;

@Repository
public interface BookingRepository extends JpaRepository<Bookings, Long> {

	@Query("From Bookings where trainBook.trainId=:trainId and  section=:section and activeIndicator=:activeIndicator")
	List<Bookings> findAllByTrainBookAndSectionAndActiveIndicator(Long trainId, String section,
			ActiveIndicator activeIndicator);

	@Query("From Bookings where userBook.userId=:userId and activeIndicator=:activeIndicator")
	List<Bookings> findAllByUserIdAndActiveIndicator(Long userId, ActiveIndicator activeIndicator);

	@Query("From Bookings where userBook.userId=:userId and section = :section and activeIndicator=:activeIndicator")
	List<Bookings> findAllByUserIdAndSectionAndActiveIndicator(Long userId, String section,
			ActiveIndicator activeIndicator);
	
	@Query("From Bookings where trainBook.trainId=:trainId and userBook.userId=:userId and activeIndicator=:activeIndicator")
	List<Bookings> findAllByTrainBookAndUserIdAndActiveIndicator(Long trainId, Long userId,
			ActiveIndicator activeIndicator);

}
