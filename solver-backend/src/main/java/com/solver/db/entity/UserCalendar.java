package com.solver.db.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCalendar extends BaseEntity{
	String possibleTime;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
}
