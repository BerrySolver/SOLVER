package com.solver.db.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message extends BaseEntity{
	private String content;
	
	@ManyToOne
	@JoinColumn(name="sendUserId")
	private User sendUser;
	
	@ManyToOne
	@JoinColumn(name="receiveUserId")
	private User receiveUser;
	
	@ManyToOne
	@JoinColumn(name="type")
	private Code code;
}
