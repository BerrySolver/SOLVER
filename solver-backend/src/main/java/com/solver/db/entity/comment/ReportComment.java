package com.solver.db.entity.comment;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReportComment extends BaseEntity{
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="reporterUserId")
	private User reporterUser;
	
	@ManyToOne
	@JoinColumn(name="commentId")
	private Comment comment;
}
