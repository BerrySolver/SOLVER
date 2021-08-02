package com.solver.db.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GroupApplicant extends BaseEntity{
	@ManyToOne
	@JoinColumn(name="groupId")
	private Group group;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
}
