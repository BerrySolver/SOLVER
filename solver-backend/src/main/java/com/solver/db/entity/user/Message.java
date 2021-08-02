package com.solver.db.entity.user;

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
