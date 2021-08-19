package com.solver.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.AnswerCreatePostReq;
import com.solver.api.request.AnswerUpdatePatchReq;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.Notification;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.code.PointCodeRepository;
import com.solver.db.repository.question.QuestionRepository;
import com.solver.db.repository.user.NotificationRepository;
import com.solver.db.repository.user.PointLogRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class AnswerServiceImpl implements AnswerService{
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	@Autowired
	PointCodeRepository pointCodeRepository;
	
	@Autowired
	PointLogRepository pointLogRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	CodeRepository codeRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Override
	public void createAnswer(String accessToken, AnswerCreatePostReq answerCreatePostReq, String questionId, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		Long kakaoId = tokenResponse.getKakaoId();
		
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
		answer.setContent(answerCreatePostReq.getContent());
		answer.setRegDt(new Date(System.currentTimeMillis()));
		answer.setUser(user);
		
		answerRepository.save(answer);
		
		// 포인트 배당
		List<Answer> list = answerRepository.findByQuestionIdOrderByRegDtAsc(questionId);
		PointLog pointLog = new PointLog();
		PointCode pointCode = null;
		
		if (list.size() == 1) { // 첫 답변일 때
			pointCode = pointCodeRepository.findByPointCode("002");			
		} else { // 이후 답변일 때
			pointCode = pointCodeRepository.findByPointCode("000");	
		}
		pointLog.setId(RandomIdUtil.makeRandomId(13));
		pointLog.setRegDt(new Date(System.currentTimeMillis()));
		pointLog.setPointCode(pointCode);
		pointLog.setUser(user);		
		
		pointLogRepository.save(pointLog);
		
		// 알림 배당 : 답변을 남긴 질문자에게 알림을 배당함
		Notification notification = new Notification();
		notification.setId(RandomIdUtil.makeRandomId(13));
		notification.setQuestion(question);
		notification.setRegDt(new Date(System.currentTimeMillis()));
		
		Code notiCode = codeRepository.findByCode("060");
		notification.setCode(notiCode);
		
		Optional<Question> q = questionRepository.findById(questionId);
		notification.setUser(q.get().getUser());
		
		notificationRepository.save(notification);
	}

	@Override
	public boolean deleteAnswer(String accessToken, String answerId, HttpServletResponse response) {
		//작성자인지 확인
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		Long kakaoId = tokenResponse.getKakaoId();
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		Answer answer = answerRepository.findById(answerId).orElse(null);
		
		//잘못된 요청 예외처리
		if(answer == null) {
			return false;
		}
		
		//다른 유저의 답변을 삭제하는 경우
		if(!answer.getUser().getId().equals(user.getId())) {
			return false;
		}
		
		answerRepository.deleteById(answerId);
		
		return true;
	}

	@Override
	public boolean updateAnswer(String accessToken, String answerId, AnswerUpdatePatchReq answerUpdatePatchReq, HttpServletResponse response) {
		//작성자인지 확인
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		Long kakaoId = tokenResponse.getKakaoId();
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		Answer answer = answerRepository.findById(answerId).orElse(null);
		
		//잘못된 요청 예외처리
		if(answer == null) {
			return false;
		}
		
		//다른 유저의 답변을 수정하는 경우
		if(!answer.getUser().getId().equals(user.getId())) {
			return false;
		}
		
		answer.setContent(answerUpdatePatchReq.getContent());
		
		Answer resultAnswer = answerRepository.save(answer);
		
		if(resultAnswer == null)
			return false;
		
		return true;
	}

	@Override
	public List<Answer> getAnswerList(String questionId) {
		List<Answer> answerList = answerRepository.findByQuestionIdOrderByRegDtAsc(questionId);
		
		if(answerList == null) {
			return null;
		}
		
		return answerList;
	}

	@Override
	public Optional<Answer> getById(String answerId) {
		Optional<Answer> answer = answerRepository.findById(answerId);
		
		return answer;
	}

	@Override
	public Answer selectAnswer(String token, Question question, Answer answer) {
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		// 유저 인증에서 오류가 있는 경우
		if (user == null) {
			return null;
		}
		
		String questionId = question.getId();
		
		// 포인트 배당
		List<Answer> list = answerRepository.findByQuestionIdOrderByRegDtAsc(questionId);
		PointLog pointLog = new PointLog();
		PointCode pointCode = null;
		
		pointCode = pointCodeRepository.findByPointCode("004");	
		pointLog.setId(RandomIdUtil.makeRandomId(13));
		pointLog.setRegDt(new Date(System.currentTimeMillis()));
		pointLog.setPointCode(pointCode);
		pointLog.setUser(answer.getUser());		
		
		pointLogRepository.save(pointLog);
		
		// 알림 배당 : 답변을 남긴 질문자에게 알림을 배당함
		Notification notification = new Notification();
		notification.setId(RandomIdUtil.makeRandomId(13));
		notification.setQuestion(question);
		notification.setRegDt(new Date(System.currentTimeMillis()));
		
		Code notiCode = codeRepository.findByCode("065");
		notification.setCode(notiCode);
		
		Optional<Question> q = questionRepository.findById(questionId);
		notification.setUser(answer.getUser());
		
		notificationRepository.save(notification);
		
		// 채택 상태 변경
		Code questionType = new Code();
		questionType = codeRepository.findByCode("040");
		Code answerType = new Code();
		answerType = codeRepository.findByCode("052");
		
		question.setCode(questionType);
		answer.setCode(answerType);
		
		questionRepository.save(question);
		return answerRepository.save(answer);
	}

}
