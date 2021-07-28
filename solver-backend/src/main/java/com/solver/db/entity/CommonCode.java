package com.solver.db.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonCode {
	@Id
	private String commonCode;
	
	private String commonCodeName;
	
	@OneToMany(mappedBy="commonCode", cascade = {CascadeType.ALL}, orphanRemoval = true)
	private List<Code> code;
}
