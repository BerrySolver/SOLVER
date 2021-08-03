package com.solver.db.entity.group;

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
public class GroupApplicant extends BaseEntity{
	@ManyToOne
	@JoinColumn(name="groupId")
	private GroupInfo group;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
}
