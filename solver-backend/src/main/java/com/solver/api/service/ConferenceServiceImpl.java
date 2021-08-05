package com.solver.api.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.conference.Conference;
import com.solver.db.entity.conference.ConferenceLog;
import com.solver.db.entity.user.User;
import com.solver.db.repository.conference.ConferenceLogRepository;
import com.solver.db.repository.conference.ConferenceParticipantRepository;
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
	
	@Autowired
	ConferenceParticipantRepository conferenceParticipantRepository;
	
	@Autowired
	ConferenceLogRepository conferenceLogRepository;

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

	@Override
	public int goOutConference(String accessToken, String conferenceId) {
		/* 회의 주관자인 경우의 예외처리가 필요할까? */
		
		
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
		
		ConferenceLog conferenceLog = new ConferenceLog();
		
		String conferenceLogId = "";
		
		while(true) {
			conferenceLogId = RandomIdUtil.makeRandomId(13);
			
			if(conferenceLogRepository.findById(conferenceLogId).orElse(null) == null)
				break;
		}
		
		Code code = new Code();
		code.setCode("031");
		
		conferenceLog.setConference(conference);
		conferenceLog.setUser(user);
		conferenceLog.setRegDt(new Date(System.currentTimeMillis()));
		conferenceLog.setCode(code);
		conferenceLog.setId(conferenceLogId);
		
		conference.setCount(conference.getCount()-1);
		
		conferenceParticipantRepository.deleteByUserId(user.getId());
		conferenceRepository.save(conference);
		conferenceLogRepository.save(conferenceLog);
		
		return 3;
	}

	@Override
	public int deleteConference(String accessToken, String conferenceId) {
		/* 회의 주관자인 경우의 예외처리가 필요할까? */
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
		
		if(conferenceRepository.findByIdAndQuestionUserId(conference.getId(), user.getId()).orElse(null) == null) {
			return 2;
		}
		
		/* 현재 화상회의 종료시에 conference 테이블에서 해당 데이터를 삭제하는데
		 * conference_log와 FK 로 연결되어 있어 conference 데이터를 삭제할 때 
		 * conference_log의 해당 화상회의 관련 데이터도 삭제되는 문제가 있음
		 *  */
		
//		conferenceRepository.deleteById(conferenceId);
			
		return 3;
	}

}
