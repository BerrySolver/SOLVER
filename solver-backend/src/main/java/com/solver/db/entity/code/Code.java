package com.solver.db.entity.code;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.conference.Conference;
import com.solver.db.entity.conference.ConferenceLog;
import com.solver.db.entity.conference.ConferenceParticipant;
import com.solver.db.entity.group.GroupComment;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.Message;
import com.solver.db.entity.user.Notification;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Code {
	@Id
	private String code;
	
	private String codeName;
	private boolean useYn;
	
	@ManyToOne
	@JoinColumn(name="commonCode")
	private CommonCode commonCode;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<User> user;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Category> category;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Notification> notification;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Message> message;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<PointLog> pointLog;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Question> question;
	
	@OneToMany(mappedBy="mainCategory", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Question> questionMainCategory;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Answer> answer;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Conference> conference;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<ConferenceParticipant> conferenceParticipant;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<ConferenceLog> conferenceLog;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<GroupComment> groupComment;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<PointCode> pointCode;
}
