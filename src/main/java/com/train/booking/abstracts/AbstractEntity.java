package com.train.booking.abstracts;

import java.util.Date;

import com.train.booking.enums.ActiveIndicator;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * Abstract class used for common fields
 * 
 * @author Akshay Gujar
 *
 */
@Data
public abstract class AbstractEntity {

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
