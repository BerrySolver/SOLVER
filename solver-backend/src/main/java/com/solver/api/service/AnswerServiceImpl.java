package com.solver.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.AnswerCreateGetReq;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class AnswerServiceImpl implements AnswerService{
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;

	@Override
	public void createAnswer(String accessToken, AnswerCreateGetReq answerCreateGetReq, String questionId) {
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		Answer answer = new Answer();
		
		Code code = new Code();
		
		code.setCode("050");
		
		String id = "";
		
		while(true) {
			id = RandomIdUtil.makeRandomId(13);
			
			if(answerRepository.findById(id).orElse(null) == null)
				break;
		}
		
		Question question = new Question();
		question.setId(questionId);
		
		answer.setQuestion(question);
		answer.setId(id);
		answer.setCode(code);
		answer.setContent(answerCreateGetReq.getContent());
		answer.setRegDt(new Date(System.currentTimeMillis()));
		answer.setUser(user);
		
		answerRepository.save(answer);
	}

}
