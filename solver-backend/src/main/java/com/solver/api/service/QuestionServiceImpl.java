package com.solver.api.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.common.auth.KakaoUtil;
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
	
	@Autowired
	KakaoUtil kakaoUtil;

	// 질문 목록 조회
	@Override
	public List<Question> getQuestionList(QuestionGetListReq questionGetListReq) {
		// 대분류, 소분류, 질문 상태는 외래키 필드이기 때문에 객체를 구해서 진행
		Code mainCategory = codeRepository.findByCode(questionGetListReq.getMainCategory());
		Category subCategory = categoryRepository.findBySubCategoryCode(questionGetListReq.getSubCategory());
		Code type = codeRepository.findByCode(questionGetListReq.getType());
		
		List<Question> questionList = questionRepositorySupport.findDynamicQueryQuestion(
				mainCategory, subCategory, questionGetListReq.getQuery(), questionGetListReq.getDifficulty(), type, questionGetListReq.getMode());
		
		return questionList;
	}
	
	// 질문 생성
	@Override
	public Question createQuestion(QuestionPostReq questionPostReq, String token) {
		// 질문 Id 생성
		Question question = new Question();
		String questionId = "";
		
		while(true) {
			questionId = RandomIdUtil.makeRandomId(13);
			
			if(questionRepository.findById(questionId).orElse(null) == null)
				break;
		}
		// 외래키 참조값 생성
		Optional<User> user = userRepository.findByKakaoId(kakaoUtil.getKakaoUserIdByToken(token));
		
		if (user.orElse(null) == null) {
			return null;
		}
		Code type = new Code();
		Code mainCategory = new Code();
		Category subCategory = new Category();
		
		type = codeRepository.findByCode("042");
		mainCategory = codeRepository.findByCode(questionPostReq.getMainCategory());
		subCategory = categoryRepository.findBySubCategoryCode(questionPostReq.getSubCategory());
		
		question.setId(questionId);
		question.setUser(user.get());
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
	
	// 질문 수정
	@Override
	public Question updateQuestion(QuestionPatchReq questionPatchReq, Question question, String token) {
		Optional<User> user = userRepository.findByKakaoId(kakaoUtil.getKakaoUserIdByToken(token));
		
		// 작성자가 현재 내가 아니면 null을 반환, controller에서 403에러 띄우게 됨
		if (!user.get().getId().equals(question.getUser().getId())) {
			return null;
		}
		
		Code mainCategory = new Code();
		Category subCategory = new Category();
		
		mainCategory = codeRepository.findByCode(questionPatchReq.getMainCategory());
		subCategory = categoryRepository.findBySubCategoryCode(questionPatchReq.getSubCategory());
		
		question.setTitle(questionPatchReq.getTitle());
		question.setContent(questionPatchReq.getContent());
		question.setMainCategory(mainCategory);
		question.setSubCategory(subCategory);
		question.setDifficulty(questionPatchReq.getDifficulty());
		
		return questionRepository.save(question);
	}
	
	// 질문 삭제
	@Override
	public void deleteQuestion(Question question, String token) {
		Optional<User> user = userRepository.findByKakaoId(kakaoUtil.getKakaoUserIdByToken(token));
		
		// 작성자가 현재 내가 아니면 에러를 던져줌
		if (!user.get().getId().equals(question.getUser().getId())) {
			throw new AccessDeniedException(null);
		}
		
		questionRepository.deleteById(question.getId());
		
		return;
	}
	
	// 내 질문 목록 조회
	@Override
	public List<Question> getMyQuestionList(String token) {
		Optional<User> user = userRepository.findByKakaoId(kakaoUtil.getKakaoUserIdByToken(token));
		
		List<Question> questionList = questionRepository.findByUserId(user.get().getId());
		
		return questionList;
	}

}
