package com.solver.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ConferenceParticipant extends BaseEntity{
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
