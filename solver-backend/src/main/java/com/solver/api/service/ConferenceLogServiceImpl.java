package com.solver.api.service;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.ConferenceLogPostReq;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.conference.Conference;
import com.solver.db.entity.conference.ConferenceLog;
import com.solver.db.entity.user.User;
import com.solver.db.repository.conference.ConferenceLogRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class ConferenceLogServiceImpl implements ConferenceLogService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ConferenceLogRepository conferenceLogRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	
	@Override
	public int createConferenceLog(String accessToken, String conferenceId, HttpServletResponse response, ConferenceLogPostReq conferenceLogPostReq) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		User user = userRepository.findByKakaoId(kakaoId).get();
		
		Code code = new Code();
		code.setCode(conferenceLogPostReq.getType());
		
		String id = "";
		
		while(true) {
			id = RandomIdUtil.makeRandomId(13);
			
			if(conferenceLogRepository.findById(id).orElse(null) == null)
				break;
		}
		
		Conference conference = new Conference();
		conference.setId(conferenceId);
		
		ConferenceLog conferenceLog = new ConferenceLog();
		conferenceLog.setCode(code);
		conferenceLog.setUser(user);
		conferenceLog.setConference(conference);
		conferenceLog.setRegDt(new Date(System.currentTimeMillis()));
		conferenceLog.setId(id);
		
		conferenceLogRepository.save(conferenceLog);
		
		return 3;
	}

}
