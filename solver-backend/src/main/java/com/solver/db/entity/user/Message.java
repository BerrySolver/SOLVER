package com.solver.db.entity.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.code.Code;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Message extends BaseEntity{
	private String content;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="sendUserId")
	private User sendUser;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="receiveUserId")
	private User receiveUser;
	
	private String questionId;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
	
	private Date regDt;
}
