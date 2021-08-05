package com.solver.db.entity.code;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="code")
	private Code code;
	
	@JsonIgnore
	@OneToMany(mappedBy="subCategory", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<Question> questionSubCategory;
	
	@JsonIgnore
	@OneToMany(mappedBy="category", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
	private List<FavoriteField> favoriteField;
}
