package com.solver.db.entity.code;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.solver.db.entity.question.Question;

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
	
	@OneToMany(mappedBy="subCategoryCode", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Question> questionSubCategory;
}
