package com.solver.db.entity.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PaidSolver implements Serializable{
	@EmbeddedId
	PaidSolverId paidSolverId;
	
	@MapsId("paidSolverId")
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	private Date startTime;
	private Date endTime;
}
