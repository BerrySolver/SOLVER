package com.solver.db.entity.group;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.solver.db.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GroupConference extends BaseEntity{
	private Date regDt;
	private int count;
	
	@ManyToOne
	@JoinColumn(name="groupId")
	private GroupInfo group;
}
