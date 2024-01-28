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
 * User Entity to store user details 
 * Extends abstract entity which contains all audit columns
 */
@Entity
@Data
@NoArgsConstructor
public class UserBook{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="userId")
	private Long userId;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@Column
	private String emailAddress;

	@JsonIgnore
	@OneToMany(mappedBy = "userBook")
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
