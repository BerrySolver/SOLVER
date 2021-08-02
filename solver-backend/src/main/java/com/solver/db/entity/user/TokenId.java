package com.solver.db.entity.user;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class TokenId implements Serializable{
	private String tokenId;
}
