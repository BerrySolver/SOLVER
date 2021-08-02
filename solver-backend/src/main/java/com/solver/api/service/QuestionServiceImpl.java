package com.solver.api.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;
import com.solver.db.repository.code.CategoryRepository;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.question.QuestionRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CodeRepository codeRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	// 질문 생성
	@Override
	public Question createQuestion(QuestionPostReq questionPostReq) {
		// 질문 Id 생성
		Question question = new Question();
		String questionId = "";
		
		while(true) {
			questionId = RandomIdUtil.makeRandomId(13);
			
			if(questionRepository.findById(questionId).orElse(null) == null)
				break;
		}
		
		// 외래키 참조값 생성
		Optional<User> user = Optional.empty();
		Code type = new Code();
		Code mainCategory = new Code();
		Category subCategory = new Category();
		
		user = userRepository.findById("1q2w3e");
		if (user.orElse(null) == null) {
			return null;
		}
		type = codeRepository.findByCode("042");
		mainCategory = codeRepository.findByCode(questionPostReq.getMainCategory());
		subCategory = categoryRepository.findBySubCategoryCode(questionPostReq.getSubCategory());
		
		question.setId(questionId);
		question.setUser(user.orElse(null));
		question.setTitle(questionPostReq.getTitle());
		question.setContent(questionPostReq.getContent());
		question.setCode(type);
		question.setMainCategory(mainCategory);
		question.setSubCategory(subCategory);
		question.setDifficulty(questionPostReq.getDifficulty());
		question.setRegDt(new Date(System.currentTimeMillis()));
		question.setExpirationTime(questionPostReq.getExpirationTime());
		question.setConferenceOpened(false);
		question.setReadCount(0);
		
		return questionRepository.save(question);
	}
	
	// 질문 상세조회
	@Override
	public Optional<Question> getById(String questionId) {
		Optional<Question> question = questionRepository.findById(questionId);
		if (question.orElse(null) == null) {
			return null;
		}
		
		return question;
	}

	@Override
	public Question updateQuestion(QuestionPatchReq questionPatchReq, Question question) {
		question.setTitle(questionPatchReq.getTitle());
		question.setContent(questionPatchReq.getContent());
		question.setDifficulty(questionPatchReq.getDifficulty());
		
		return questionRepository.save(question);
	}

	@Override
	public void deleteQuestion(Question question) {
		questionRepository.deleteById(question.getId());
		
		return;
	}

}
