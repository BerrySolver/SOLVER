package com.solver.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.conference.Conference;
import com.solver.db.entity.user.User;
import com.solver.db.repository.conference.ConferenceRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class ConferenceServiceImpl implements ConferenceService{
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ConferenceRepository conferenceRepository;

	@Override
	public int updateConference(String accessToken, String conferenceId) {
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);
		
		if(user == null) {
			return 0;
		}
		
		Conference conference = conferenceRepository.findById(conferenceId).orElse(null);
		
		if(conference == null) {
			return 1;
		}
		
		if(!user.getId().equals(conference.getQuestionUser().getId())) {
			return 2;
		}
		
		Code code = new Code();
		
		code.setCode("010");
		
		conference.setCode(code);
		
		conferenceRepository.save(conference);
		
		return 3;
	}

}
