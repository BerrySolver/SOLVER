package com.solver.db.entity.question;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FavoriteQuestion extends BaseEntity{
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="questionId")
	private Question question;
}
