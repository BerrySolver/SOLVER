package com.solver.db.entity.conference;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conference extends BaseEntity{
	private Date regDt;
	private int maxCount;
	private int count;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="questionId")
	private Question question;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="questionUserId")
	private User questionUser;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="answerUserId")
	private User answerUser;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
	
	@JsonManagedReference
	@OneToMany(mappedBy="conference", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<ConferenceParticipant> conferenceParticipant;
	
	@JsonManagedReference
	@OneToMany(mappedBy="conference", orphanRemoval = true)
	private List<ConferenceLog> conferenceLog;
}
