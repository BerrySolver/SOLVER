package com.solver.db.entity.code;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.PointLog;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PointCode {
	@Id
	private String pointCode;
	
	@ManyToOne
	@JoinColumn(name="code")
	private Code code;
	
	private int value;
	private String pointName;
	private boolean userYn;
	
	@OneToMany(mappedBy="pointCode", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<PointLog> pointLog;
}
