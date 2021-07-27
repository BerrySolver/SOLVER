package com.solver.db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseEntity{
	private String nickname;
	private String introduction;
	private String linkText;
	private String profileUrl;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteUser> favoriteUser;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Question> question;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteQuestion> favoriteQuestion;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<BookmarkQuestion> bookmarkQuestion;
	
	@OneToMany(mappedBy="reporterUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportQuestion> reportQuestion;
	
	@OneToMany(mappedBy="reportedUser", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportQuestion> reportedQuestion;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Answer> answer;
	
	@OneToMany(mappedBy="user", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteAnswer> favoriteAnswer;
}
