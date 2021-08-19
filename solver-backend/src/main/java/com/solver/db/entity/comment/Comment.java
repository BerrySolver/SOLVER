package com.solver.db.entity.comment;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.solver.db.entity.BaseEntity;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment extends BaseEntity{

	@Column(columnDefinition = "LONGTEXT")
	private String content;
	private Date regDt;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="answerId")
	private Answer answer;
}
