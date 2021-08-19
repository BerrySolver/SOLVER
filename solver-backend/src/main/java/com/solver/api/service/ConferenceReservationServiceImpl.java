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
import com.solver.db.entity.conference.ConferenceReservation;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;
import com.solver.db.repository.conference.ConferenceReservationRepository;
import com.solver.db.repository.question.QuestionRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class ConferenceReservationServiceImpl implements ConferenceReservationService{
	
	@Autowired
	ConferenceReservationRepository conferenceReservationRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	KakaoUtil kakaoUtil;

	@Override
	public ConferenceReservation createConferenceReservation(String token, Question question, HttpServletResponse response) {
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

			if (conferenceReservationRepository.findById(id).orElse(null) == null)
				break;
		}
		
		// 이미 참관 신청한 경우
		if (conferenceReservationRepository.findByUserIdAndQuestionId(user.getId(), question.getId()).orElse(null) != null) {
			return null;
		}

		ConferenceReservation conferenceReservation = new ConferenceReservation();
		conferenceReservation.setQuestion(question);
		conferenceReservation.setUser(user);
		conferenceReservation.setId(id);

		return conferenceReservationRepository.save(conferenceReservation);
	}

	@Override
	public void deleteConferenceReservation(String token, Question question, HttpServletResponse response) {
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
		Optional<ConferenceReservation> conferenceReservation = conferenceReservationRepository.findByUserIdAndQuestionId(user.get().getId(), question.getId());
		
		try {
			conferenceReservationRepository.deleteById(conferenceReservation.get().getId());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException();
		}
		
		return;
		
	}
	
	@Override
	public List<ConferenceReservation> getReservationList(String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];

		TokenResponse tokenResponse = new TokenResponse();

		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();

		if (tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		User user = userRepository.findByKakaoId(kakaoId).orElse(null);

		if (user == null) {
			return null;
		}
		
		Date nowDate = new Date(System.currentTimeMillis()-(3600*1000));
		Date endDate = new Date(System.currentTimeMillis()+(604800*1000));
		
		List<ConferenceReservation> list = conferenceReservationRepository.findAllByUserId(user.getId(), nowDate, endDate);
		
		list.sort((o1, o2)->{
			return o1.getStartDt().compareTo(o2.getStartDt());
		});
		
		for (ConferenceReservation conferenceReservation : list) {
			System.out.println(conferenceReservation.getId()+" "+conferenceReservation.getStartDt()+" "+conferenceReservation.getQuestion().getId());
		}
		
		return list;
	}

}
