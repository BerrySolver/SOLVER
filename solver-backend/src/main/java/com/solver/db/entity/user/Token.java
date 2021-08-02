package com.solver.db.entity.user;

import java.io.Serializable;

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
public class Token implements Serializable{
	@EmbeddedId
	TokenId tokenId;
	
	@MapsId("tokenId")
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	private String refreshToken;
	private String accessToken;
}
