package com.solver.api.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.solver.api.request.QuestionGetListReq;
import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.api.response.QuestionListRes;
import com.solver.api.response.QuestionRecommendRes;
import com.solver.api.response.QuestionRes;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.code.FavoriteField;
import com.solver.db.entity.question.BookmarkQuestion;
import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.code.CategoryRepository;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.question.BookmarkQuestionRepository;
import com.solver.db.repository.question.FavoriteQuestionRepository;
import com.solver.db.repository.question.QuestionRepository;
import com.solver.db.repository.question.QuestionRepositorySupport;
import com.solver.db.repository.user.UserRepository;

import lombok.Getter;

@Service
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	QuestionRepositorySupport questionRepositorySupport;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CodeRepository codeRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	FavoriteQuestionRepository favoriteQuestionRepository;
	
	@Autowired
	BookmarkQuestionRepository bookmarkQuestionRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;

	// 질문 목록 조회
	@Override
	public QuestionListRes getQuestionList(QuestionGetListReq questionGetListReq) {
		// 대분류, 소분류, 질문 상태는 외래키 필드이기 때문에 객체를 구해서 진행
		Code mainCategory = codeRepository.findByCode(questionGetListReq.getMainCategory());
		Category subCategory = categoryRepository.findBySubCategoryCode(questionGetListReq.getSubCategory());
		Code type = codeRepository.findByCode(questionGetListReq.getType());
		
		int limit = questionGetListReq.getLimit();
		int offset = questionGetListReq.getOffset();
		
		List<Question> questionList = questionRepositorySupport.findDynamicQueryQuestion(
				mainCategory, subCategory, questionGetListReq.getQuery(), questionGetListReq.getDifficulty(), type, questionGetListReq.getMode());
		
		int totalCount = questionList.size();
		
		// 오늘 질문, 답변 개수 구하기
		Calendar calStart = Calendar.getInstance();
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		Date todayStart = new Date(calStart.getTimeInMillis());
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(Calendar.HOUR_OF_DAY, 23);
		calEnd.set(Calendar.MINUTE, 59);
		calEnd.set(Calendar.SECOND, 59);
		Date todayEnd = new Date(calEnd.getTimeInMillis());
		
		
		int todayQuestions = questionRepository.findByRegDtBetween(todayStart, todayEnd).size();
		int todayAnswers = answerRepository.findByRegDtBetween(todayStart, todayEnd).size();

		int listLimit = limit > questionList.size() - (offset*limit) ? questionList.size(): limit*(offset+1);
		
		questionList = questionList.subList(offset*limit, listLimit);
		
		return QuestionListRes.of(200, "질문 목록을 성공적으로 조회했습니다.", questionList, totalCount, todayQuestions, todayAnswers);
	}
	
	// 질문 생성
	@Override
	public Question createQuestion(QuestionPostReq questionPostReq, String token, HttpServletResponse response) {
		// 질문 Id 생성
		Question question = new Question();
		String questionId = "";
		
		while(true) {
			questionId = RandomIdUtil.makeRandomId(13);
			
			if(questionRepository.findById(questionId).orElse(null) == null)
				break;
		}
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		// 외래키 참조값 생성
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
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

		Question q = question.get();
		q.setReadCount(q.getReadCount()+1);
		questionRepository.save(q);
		
		return question;
	}
	
	// 질문 수정
	@Override
	public Question updateQuestion(QuestionPatchReq questionPatchReq, Question question, String token, HttpServletResponse response) {
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
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
	public void deleteQuestion(Question question, String token, HttpServletResponse response) {
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
		// 작성자가 현재 내가 아니면 에러를 던져줌
		if (!user.get().getId().equals(question.getUser().getId())) {
			throw new AccessDeniedException(null);
		}
		
		questionRepository.deleteById(question.getId());
		
		return;
	}
	
	// 내 질문 목록 조회
	@Override
	public List<Question> getMyQuestionList(String token, HttpServletResponse response) {
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
		List<Question> questionList = questionRepository.findByUserId(user.get().getId());
		
		return questionList;
	}

	@Override
	public List<QuestionRecommendRes> getRecommendQuestion(String accessToken, HttpServletResponse response) {	
		// 로그인 확인
		String token = accessToken.split(" ")[1];
		
		List<String> subCategory = new ArrayList<String>();
		if(!token.equals("null")) {
			TokenResponse tokenResponse = new TokenResponse();			
			tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
			Long kakaoId = tokenResponse.getKakaoId();			
			User user = userRepository.findByKakaoId(kakaoId).orElse(null);
			List<FavoriteField> fields = user.getFavoriteField(); // 나의 관심분야 가져오기	
			for (FavoriteField favoriteField : fields)
				subCategory.add(favoriteField.getCategory().getSubCategoryCode());
		}
		
		List<Question> questionList = questionRepositorySupport.findDynamicQueryQuestion(null, null, null, 0, null, "likeDesc");
		
//		List<Recommend> recommendList = new ArrayList<Recommend>();
//		for (Question question : questionList)
//			recommendList.add(new Recommend(question));
//		
//		Collections.sort(recommendList, new Comparator<Recommend>() {
//
//			@Override
//			public int compare(Recommend o1, Recommend o2) {				
//				return o2.point - o1.point;
//			}
//		});
		Collections.sort(questionList, new Comparator<Question>() {

			@Override
			public int compare(Question o1, Question o2) {
				return o2.getReadCount()-o1.getReadCount();
			}
		});
		
		List<QuestionRecommendRes> list = new ArrayList<QuestionRecommendRes>();
		int size = 0;
		
//		for (Recommend recommend : recommendList) {
//			Question q = recommend.getQuestion();
		for(Question q : questionList) {
			
			if(!token.equals("null") && !subCategory.contains(q.getSubCategory().getSubCategoryCode())) {
				// 로그인상태인데, 현 글이 내 관심분야에 없을 때는 다음 질문 확인
				continue;
			}
			
			QuestionRecommendRes res = new QuestionRecommendRes();
			res.setQuestionId(q.getId());
			res.setTitle(q.getTitle());
			res.setContent(q.getContent());
			res.setAnswerCount(favoriteQuestionRepository.findByQuestionId(q.getId()).size());
			res.setLikeCount(bookmarkQuestionRepository.findByQuestionId(q.getId()).size());
			res.setBookMarkCount(answerRepository.findByQuestionId(q.getId()).size());
			res.setReadCount(q.getReadCount());
			list.add(res);
			
			if(++size==6)
				break;
		}
		
		
		
		return list;
	}
	
//	@Getter
//	class Recommend{
//		public Question question;
//		public int point;
//		public int likeCnt;
//		public int bookMarkCnt;
//		public int answerCnt;
//		
//		public Recommend(Question q) {
//			this.question = q;
//			this.likeCnt = favoriteQuestionRepository.findByQuestionId(q.getId()).size();
//			this.bookMarkCnt = bookmarkQuestionRepository.findByQuestionId(q.getId()).size();
//			this.answerCnt = answerRepository.findByQuestionId(q.getId()).size();
//			
//			this.point = q.getReadCount() + this.likeCnt + this.bookMarkCnt + this.answerCnt; 
//		}
//	}
}
