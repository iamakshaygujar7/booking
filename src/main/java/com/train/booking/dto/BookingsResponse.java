package com.train.booking.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.train.booking.entity.TrainBook;
import com.train.booking.entity.UserBook;
import com.train.booking.enums.ActiveIndicator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BookingsResponse {
	
	private Long bookingId;
	
	private UserBook userBook;
	
	private TrainBook trainBook;
	
	private Double price;
	
	private String section;
	
	private Long seatNumber;
	
	private Date createdDate;

	private String createdBy;

	private Date lastEditedDate;

	private String lastEditedBy;

	private ActiveIndicator activeIndicator;

	private String comments;

}
