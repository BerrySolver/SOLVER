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
public class GroupArticle extends BaseEntity{
	private String text;
	private Date regDt;
	private String title;
	
	@ManyToOne
	@JoinColumn(name="groupId")
	private Group group;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@OneToMany(mappedBy="groupArticle", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<GroupArticleFile> groupArticleFile;
}
