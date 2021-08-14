package com.solver.api.service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.question.BookmarkQuestion;
import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;
import com.solver.db.repository.code.PointCodeRepository;
import com.solver.db.repository.question.BookmarkQuestionRepository;
import com.solver.db.repository.question.QuestionRepository;
import com.solver.db.repository.user.PointLogRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class BookmarkQuestionServiceImpl implements BookmarkQuestionService{
	
	@Autowired
	BookmarkQuestionRepository bookmarkQuestionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	@Autowired
	PointCodeRepository pointCodeRepository;;
	
	@Autowired
	PointLogRepository pointLogRepository;
	
	// 북마크 확인
	@Override
	public boolean checkBookmarkQuestion(String token, Question question) {
		if (!token.equals("null")) {
			TokenResponse tokenResponse = new TokenResponse();
			tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
	
			Long kakaoId = tokenResponse.getKakaoId();
			
			Optional<User> user = userRepository.findByKakaoId(kakaoId);
			
			for (BookmarkQuestion bookmarkQuestion : question.getBookmarkQuestion()) {
				if (bookmarkQuestion.getUser().getId().equals(user.get().getId())) {
					return true;
				}
			}
			
			return false;
		
		} else {
			return false;
		}
	}
	
	@Override
	public boolean createBookmark(String accessToken, Question question, HttpServletResponse response) {
		//작성자인지 확인
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		Long kakaoId = tokenResponse.getKakaoId();
		
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);
		
		if(user == null) {
			return false;
		}
		
		//id 생성
		String id = "";
		
		while(true) {
			id = RandomIdUtil.makeRandomId(13);
			
			if(bookmarkQuestionRepository.findById(id).orElse(null) == null)
				break;
		}

		// 포인트 배당
		PointLog pointLog = new PointLog();
		PointCode pointCode = null;
		
		pointCode = pointCodeRepository.findByPointCode("008");	
		pointLog.setId(RandomIdUtil.makeRandomId(13));
		pointLog.setRegDt(new Date(System.currentTimeMillis()));
		pointLog.setPointCode(pointCode);
		pointLog.setUser(question.getUser());		
		
		pointLogRepository.save(pointLog);
		
		// 북마크 등록
		BookmarkQuestion bookmarkQuestion = new BookmarkQuestion();
		
		bookmarkQuestion.setId(id);
		bookmarkQuestion.setQuestion(question);
		bookmarkQuestion.setUser(user);
		
		bookmarkQuestionRepository.save(bookmarkQuestion);
		
		return true;
	}

	@Override
	public void deleteBookmark(Question question, String token, HttpServletResponse response) {
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
		Optional<BookmarkQuestion> bookmarkQuestion = bookmarkQuestionRepository.findByUserIdAndQuestionId(user.get().getId(), question.getId());
		
		try {
			bookmarkQuestionRepository.deleteById(bookmarkQuestion.get().getId());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}
		
		return;
	}

}
