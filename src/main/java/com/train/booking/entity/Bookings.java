package com.train.booking.entity;

import java.util.Date;
import java.util.List;

import com.train.booking.enums.ActiveIndicator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Bookings{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private UserBook userBook;
	
	@ManyToOne
	@JoinColumn(name="trainId")
	private TrainBook trainBook;
	
	
	
	@OneToMany(mappedBy = "bookings", cascade = CascadeType.ALL) 
	private List<BookingDetails> bookingDetails;
	
	@Column
	private String section;
	
	@Column
	private Date createdDate;

	@Column
	private String createdBy;

	@Column
	private Date lastEditedDate;

	@Column
	private String lastEditedBy;

	@Enumerated(EnumType.STRING)
	@Column
	private ActiveIndicator activeIndicator = ActiveIndicator.A;

	@Column
	private String comments;

}
