package com.train.booking.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.booking.entity.TrainBook;
import com.train.booking.enums.ActiveIndicator;

@Repository
public interface TrainRepository extends JpaRepository<TrainBook, Long> {

	public Optional<TrainBook> findByTrainNameAndActiveIndicator(String trainName, ActiveIndicator activeIndicator);

	public List<TrainBook> findAllByActiveIndicator(ActiveIndicator activeIndicator);

}
