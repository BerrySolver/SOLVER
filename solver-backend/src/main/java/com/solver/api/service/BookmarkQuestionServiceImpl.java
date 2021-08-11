package com.solver.api.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.question.BookmarkQuestion;
import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;
import com.solver.db.repository.question.BookmarkQuestionRepository;
import com.solver.db.repository.question.QuestionRepository;
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
