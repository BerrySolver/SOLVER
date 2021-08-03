package com.solver.db.repository.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.question.QQuestion;
import com.solver.db.entity.question.Question;

@Repository
public class QuestionRepositorySupport {
	
	@Autowired
	private JPAQueryFactory queryFactory;
	QQuestion question = QQuestion.question;
	
	public List<Question> findDynamicQueryQuestion(
			Code mainCategory, Category subCategory, String query, int difficulty, Code type, String mode){
				return queryFactory
						.selectFrom(question)
						.where(eqMainCategory(mainCategory),
								eqSubCategory(subCategory),
								eqDifficulty(difficulty),
								inQuery(query),
								eqType(type))
						.fetch();
	}

	private Predicate eqMainCategory(Code mainCategory) {
		if (mainCategory == null) {
			return null;
		}
		return question.mainCategory.eq(mainCategory);
	}
	
	private Predicate eqSubCategory(Category subCategory) {
		if (subCategory == null) {
			return null;
		}
		return question.subCategory.eq(subCategory);
	}
	
	private Predicate inQuery(String query) {
		if (query == null) {
			return null;
		}
		return question.content.contains(query).or(question.title.contains(query));
	}
	
	private Predicate eqDifficulty(int difficulty) {
		if (difficulty == 0) {
			return null;
		}
		return question.difficulty.eq(difficulty);
	}
	
	private Predicate eqType(Code type) {
		if (type == null) {
			return null;
		}
		return question.code.eq(type);
	}
	 
}
