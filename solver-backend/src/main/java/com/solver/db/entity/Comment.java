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
public class Comment extends BaseEntity{
	private String content;
	private Date regDt;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="answerId")
	private Answer answer;
	
	@OneToMany(mappedBy="comment", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<ReportComment> reportComment;
}
