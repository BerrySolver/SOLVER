package com.solver.db.entity.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Token implements Serializable{
	@Id
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	private String refreshToken;
	private String accessToken;
}
