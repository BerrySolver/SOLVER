package com.solver.api.service;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.answer.FavoriteAnswer;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.user.Notification;
import com.solver.db.entity.user.PointLog;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.answer.FavoriteAnswerRepository;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.code.PointCodeRepository;
import com.solver.db.repository.user.NotificationRepository;
import com.solver.db.repository.user.PointLogRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class FavoriteAnswerServiceImpl implements FavoriteAnswerService {

	@Autowired
	FavoriteAnswerRepository favoriteAnswerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AnswerRepository answerRepository;

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

	@Override
	public int createFavoriteAnswer(String accessToken, String answerId, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		// 유저 인증에서 오류가 있는 경우?
		if (user == null) {
			return 0;
		}

		Answer answer = answerRepository.findById(answerId).orElse(null);

		// 없는 답변에 대한 것인 경우
		if (answer == null) {
			return 1;
		}

		String id = "";

		while (true) {
			id = RandomIdUtil.makeRandomId(13);

			if (favoriteAnswerRepository.findById(id).orElse(null) == null)
				break;
		}

		// 이미 좋아요 한 답변인 경우
		if (favoriteAnswerRepository.findByUserIdAndAnswerId(user.getId(), answer.getId()).orElse(null) != null) {
			return 2;
		}
		
		// 알림 배당 : 답변에 좋아요를 하면 답변 작성자에게 알림이 갑니다.
		Notification notification = new Notification();
		notification.setId(RandomIdUtil.makeRandomId(13));
		notification.setQuestion(answer.getQuestion());
		
		Code notiCode = codeRepository.findByCode("063");
		notification.setCode(notiCode);
		notification.setUser(answer.getUser());
		
		notificationRepository.save(notification);
		
		// 포인트 배당
		PointLog pointLog = new PointLog();
		PointCode pointCode = null;
		
		pointCode = pointCodeRepository.findByPointCode("007");	
		pointLog.setId(RandomIdUtil.makeRandomId(13));
		pointLog.setRegDt(new Date(System.currentTimeMillis()));
		pointLog.setPointCode(pointCode);
		pointLog.setUser(answer.getUser());		
		
		pointLogRepository.save(pointLog);

		// 답변 좋아요 등록
		FavoriteAnswer favoriteAnswer = new FavoriteAnswer();
		favoriteAnswer.setAnswer(answer);
		favoriteAnswer.setUser(user);
		favoriteAnswer.setId(id);

		favoriteAnswerRepository.save(favoriteAnswer);

		return 3;
	}

	@Override
	public int deleteFavoriteAnswer(String accessToken, String answerId, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		// 유저 인증에서 오류가 있는 경우?
		if (user == null) {
			return 0;
		}

		Answer answer = answerRepository.findById(answerId).orElse(null);

		// 없는 답변에 대한 것인 경우
		if (answer == null) {
			return 1;
		}

		FavoriteAnswer favoriteAnswer = favoriteAnswerRepository.findByUserIdAndAnswerId(user.getId(), answer.getId()).orElse(null);

		// 이미 좋아요 한 답변인 경우
		if (favoriteAnswer == null) {
			return 2;
		}

		favoriteAnswerRepository.deleteById(favoriteAnswer.getId());

		return 3;
	}
}
