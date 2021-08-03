package com.solver.db.entity.code;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CommonCode {
	@Id
	private String commonCode;
	
	private String commonCodeName;
	
	@OneToMany(mappedBy="commonCode", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Code> code;
}
