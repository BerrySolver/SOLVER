package com.solver.db.entity;

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
public class Group extends BaseEntity{
	private String groupName;
	private int memberCount;
	private String introduce;
	private boolean conferenceOpened;
	
	@ManyToOne
	@JoinColumn(name="ownerUserId")
	private User user;
	
	@OneToMany(mappedBy="group", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<GroupApplicant> groupApplicant;
	
	@OneToMany(mappedBy="group", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<GroupMember> groupMember;
	
	@OneToMany(mappedBy="group", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<GroupConference> groupConference;
	
	@OneToMany(mappedBy="group", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<GroupArticle> groupArticle;
}
