package com.solver.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.question.BookmarkQuestion;
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
	
	@Override
	public boolean createBookmark(String accessToken, Question question) {
		//작성자인지 확인
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
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

}
