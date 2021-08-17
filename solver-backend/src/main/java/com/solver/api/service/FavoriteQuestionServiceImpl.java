package com.solver.api.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.Notification;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.code.PointCodeRepository;
import com.solver.db.repository.question.FavoriteQuestionRepository;
import com.solver.db.repository.question.QuestionRepository;
import com.solver.db.repository.user.NotificationRepository;
import com.solver.db.repository.user.PointLogRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class FavoriteQuestionServiceImpl implements FavoriteQuestionService{

	@Autowired
	FavoriteQuestionRepository favoriteQuestionRepository;

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

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	CodeRepository codeRepository;
	
	// 좋아요 확인
	@Override
	public boolean checkFavoriteQuestion(String token, Question question) {
		if (!token.equals("null")) {
			TokenResponse tokenResponse = new TokenResponse();
			tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
	
			Long kakaoId = tokenResponse.getKakaoId();
			
			Optional<User> user = userRepository.findByKakaoId(kakaoId);
			
			for (FavoriteQuestion favoriteQuestion : question.getFavoriteQuestion()) {
				if (favoriteQuestion.getUser().getId().equals(user.get().getId())) {
					return true;
				}
			}
			
			return false;
		
		} else {
			return false;
		}
	}
	
	// 좋아요
	@Override
	public FavoriteQuestion createFavoriteQuestion(String accessToken, Question question, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}


		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		// 유저 인증에서 오류가 있는 경우
		if (user == null) {
			return null;
		}

		String id = "";

		while (true) {
			id = RandomIdUtil.makeRandomId(13);

			if (favoriteQuestionRepository.findById(id).orElse(null) == null)
				break;
		}

		// 이미 좋아요 한 질문인 경우
		if (favoriteQuestionRepository.findByUserIdAndQuestionId(user.getId(), question.getId()).orElse(null) != null) {
			return null;
		}
		
		// 알림 등록 : 질문에 좋아요를 하면 질문자에게 알림이 갑니다.
		Notification notification = new Notification();
		notification.setId(RandomIdUtil.makeRandomId(13));
		notification.setQuestion(question);
		notification.setRegDt(new Date(System.currentTimeMillis()));
		
		Code notiCode = codeRepository.findByCode("062");
		notification.setCode(notiCode);
		notification.setUser(question.getUser());
		
		notificationRepository.save(notification);

		// 포인트 배당
		PointLog pointLog = new PointLog();
		PointCode pointCode = null;
		
		pointCode = pointCodeRepository.findByPointCode("006");	
		pointLog.setId(RandomIdUtil.makeRandomId(13));
		pointLog.setRegDt(new Date(System.currentTimeMillis()));
		pointLog.setPointCode(pointCode);
		pointLog.setUser(question.getUser());		
		
		pointLogRepository.save(pointLog);
		
		// 좋아요 등록
		FavoriteQuestion favoriteQuestion = new FavoriteQuestion();
		favoriteQuestion.setQuestion(question);
		favoriteQuestion.setUser(user);
		favoriteQuestion.setId(id);
		
		return favoriteQuestionRepository.save(favoriteQuestion);
	}
	
	// 좋아요 취소
	@Override
	public void deleteFavoriteQuestion(String token, Question question, HttpServletResponse response) {
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
		Optional<FavoriteQuestion> favoriteQuestion = favoriteQuestionRepository.findByUserIdAndQuestionId(user.get().getId(), question.getId());
		
		try {
			favoriteQuestionRepository.deleteById(favoriteQuestion.get().getId());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}
		
		return;
	}
	
}
