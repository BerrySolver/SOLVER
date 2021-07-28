package com.solver.db.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ConferenceLog extends BaseEntity{
	private Date regDt;
	
	@ManyToOne
	@JoinColumn(name="conferenceId")
	private Conference conference;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
}
