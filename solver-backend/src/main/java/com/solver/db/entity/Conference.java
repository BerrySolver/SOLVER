package com.solver.db.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conference extends BaseEntity{
	private Date regDt;
	private int maxCount;
	private int count;
	
	@ManyToOne
	@JoinColumn(name="questionId")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name="questionUserId")
	private User questionUser;
	
	@ManyToOne
	@JoinColumn(name="answerUserId")
	private User answerUser;
	
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
	
	@OneToMany(mappedBy="conference", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ConferenceParticipant> conferenceParticipant;
	
	@OneToMany(mappedBy="conference", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ConferenceLog> conferenceLog;
}
