package com.solver.db.entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
	@Id
	private String subCategoryCode;
	
	private String subCategoryName;
	private boolean useYN;
	
	@ManyToOne
	@JoinColumn(name="code")
	private Code code;
}
