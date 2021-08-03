package com.solver.db.repository.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.question.Question;

public interface QuestionRepository extends JpaRepository<Question, String>{
	// 대분류 - 소분류
	List<Question> findByMainCategoryAndSubCategory(Code mainCategory, Category subCategory);
	
}
