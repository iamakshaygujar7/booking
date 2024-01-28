package com.train.booking.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.train.booking.enums.ActiveIndicator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Train entity with fixed price for now
 * 
 * @author Akshay Gujar
 *
 */
@Entity
@Data
@NoArgsConstructor
public class TrainBook{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trainId;
	
	@Column
	private String trainName;

	@Column
	private String fromLocation;

	@Column
	private String toLocation;

	@Column
	private Double price;
	
	@JsonIgnore
	@OneToMany(mappedBy = "trainBook")
	private List<Bookings> bookings;
	
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
