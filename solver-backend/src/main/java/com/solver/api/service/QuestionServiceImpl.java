package com.solver.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.QuestionReq;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.Code;
import com.solver.db.entity.Question;
import com.solver.db.entity.User;
import com.solver.db.repository.QuestionRepository;
import com.solver.db.repository.UserRepository;

@Service
public class QuestionServiceImpl implements QuestionService{
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public Question createQuestion(QuestionReq questionReq) {
		Question question = new Question();
		String questionId = "";
		
		while(true) {
			questionId = RandomIdUtil.makeRandomId(13);
			
			if(questionRepository.findById(questionId).orElse(null) == null)
				break;
		}
		
		Optional<User> user = Optional.empty();
		Code code = new Code();
		
		user = userRepository.findById(questionReq.getUserId());
		if (user.orElse(null) == null) {
			return null;
		}
		
		question.setId(questionId);
		question.setUser(user.orElse(null));
		question.setTitle(questionReq.getTitle());
		question.setContent(questionReq.getContent());
//		question.setMainCategory(questionReq.getMainCategory());
//		question.setSubCategory(questionReq.getSubCategory());
		question.setDifficulty(questionReq.getDifficulty());
		question.setRegDt(questionReq.getRegDt());
		question.setExpirationTime(questionReq.getExpirationTime());
		question.setConferenceOpened(questionReq.isConferenceOpened());
		question.setReadCount(questionReq.getReadCount());
		
		questionRepository.save(question);
		return question;
	}

}
