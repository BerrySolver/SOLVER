package com.solver.db.entity.code;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
	@Id
	private String subCategoryCode;
	
	private String subCategoryName;
	private boolean useYn;
	
	@ManyToOne
	@JoinColumn(name="code")
	private Code code;
}
