package com.solver.db.entity.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.code.Code;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PointLog extends BaseEntity{
	private int usePoint;
	private String detail;
	private Date regDt;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
}
