package com.solver.db.repository.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.question.QQuestion;
import com.solver.db.entity.question.Question;

@Repository
public class QuestionRepositorySupport {
	
	// querydsl을 사용해서 동적 쿼리를 구현 (사용자가 대분류, 소분류, 검색어, 난이도, 질문 상태, 정렬 기준 중 무엇만 선택할지 모르기 때문에)
	@Autowired
	private JPAQueryFactory queryFactory;
	QQuestion question = QQuestion.question;
	
	public List<Question> findDynamicQueryQuestion(
			Code mainCategory, Category subCategory, String query, int difficulty, Code type, String mode){
				return queryFactory
						.selectFrom(question)
						// null이 들어오면 그 조건은 건너뛰게 된다.
						.where(eqMainCategory(mainCategory),
								eqSubCategory(subCategory),
								eqDifficulty(difficulty),
								inQuery(query),
								eqType(type))
						.orderBy(selectMode(mode))
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
		// 검색어를 제목이 포함하고 있거나, 내용이 포함하고 있거나
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
	
	private OrderSpecifier<?> selectMode(String mode) {
		// 답변 개수 내림차순
		if (mode.equals("answerDesc")) {
			return question.answer.size().desc();
		// 좋아요 개수 내림차순
		} else if (mode.equals("likeDesc")) {
			return question.favoriteQuestion.size().desc();
		}
		
		// 생성 시간 내림차순
		return question.regDt.desc();
	}
	 
}
