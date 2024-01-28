package com.train.booking.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.train.booking.entity.UserBook;
import com.train.booking.enums.ActiveIndicator;

@Repository
public interface UserRepository extends JpaRepository<UserBook, Long>{
	
	public Optional<UserBook> findByEmailAddressAndActiveIndicator(String email, ActiveIndicator activeIndicator);
	
	public List<UserBook> findAllByActiveIndicator(ActiveIndicator activeIndicator);

}
