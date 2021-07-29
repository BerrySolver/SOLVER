package com.solver.db.entity;

import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Token extends BaseEntity{
	private String refreshToken;
	private String accessToken;
}
