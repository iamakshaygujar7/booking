package com.train.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.train.booking.enums.ActiveIndicator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BookingDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingDetailsId;
	
	@Column
	private String currencyRef;
	
	@Column(name ="price")
	private Double price;
	
	@Column
	private Long seatNumber;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="bookingId")
	private Bookings bookings;
	
	@Enumerated(EnumType.STRING)
	@Column
	private ActiveIndicator activeIndicator = ActiveIndicator.A;

}
