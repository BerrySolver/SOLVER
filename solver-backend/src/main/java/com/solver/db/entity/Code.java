package com.solver.db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Code {
	@Id
	private String code;
	
	private String codeName;
	private boolean useYN;
	
	@ManyToOne
	@JoinColumn(name="commonCode")
	private CommonCode commonCode;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<User> user;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Category> category;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<FavoriteField> favoriteField;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Notification> notification;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Message> message;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<PointLog> pointLog;
	
	@OneToMany(mappedBy="code", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Conference> conference;
}
