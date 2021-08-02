package com.solver.db.entity.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.answer.Evaluation;
import com.solver.db.entity.answer.FavoriteAnswer;
import com.solver.db.entity.answer.ReportAnswer;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.code.FavoriteField;
import com.solver.db.entity.comment.Comment;
import com.solver.db.entity.comment.ReportComment;
import com.solver.db.entity.conference.Conference;
import com.solver.db.entity.conference.ConferenceReservation;
import com.solver.db.entity.question.BookmarkQuestion;
import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.question.ReportQuestion;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseEntity{
	private Long kakaoId;
	private String nickname;
	private String introduction;
	private String linkText;
	private String profileUrl;
	
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<FavoriteField> favoriteField;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<FavoriteUser> favoriteUser;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<UserCalendar> userCalendar;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Notification> notification;
	
	@OneToMany(mappedBy="sendUser", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Message> sendMessage;
	
	@OneToMany(mappedBy="receiveUser", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Message> receiveMessage;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<PaidSolver> paidSolver;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<PointLog> pointLog;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Question> question;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<FavoriteQuestion> favoriteQuestion;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<BookmarkQuestion> bookmarkQuestion;
	
	@OneToMany(mappedBy="reporterUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportQuestion> reportQuestion;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Answer> answer;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteAnswer> favoriteAnswer;
	
	@OneToMany(mappedBy="reporterUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportAnswer> reportAnswer;
	
	@OneToMany(mappedBy="answerUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Evaluation> evaluateAnswer;
	
	@OneToMany(mappedBy="questionUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Evaluation> evaluatedAnswer;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Comment> comment;
	
	@OneToMany(mappedBy="reporterUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportComment> reportComment;
	
	@OneToMany(mappedBy="questionUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Conference> questionConference;
	
	@OneToMany(mappedBy="answerUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Conference> answerConference;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ConferenceReservation> conferenceReservation;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Token> token;
	
}
