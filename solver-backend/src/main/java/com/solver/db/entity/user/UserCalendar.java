package com.solver.db.entity.user;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.solver.db.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserCalendar extends BaseEntity{
	String weekdayTime;
	
	String weekendTime;
	
	@OneToOne
	@JoinColumn(name="userId")
	private User user;
}
