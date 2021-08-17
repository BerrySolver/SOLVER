package com.solver.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.response.NotificationListRes;
import com.solver.api.response.NotificationRes;
import com.solver.api.response.PointLogRes;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.user.Notification;
import com.solver.db.entity.user.User;
import com.solver.db.repository.user.NotificationRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationRepository notificationRepository;

	@Override
	public List<NotificationRes> getAllNotificationList(String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}

		Long kakaoId = tokenResponse.getKakaoId();		
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);
		
		List<NotificationRes> list = new ArrayList<NotificationRes>();
		
		if(user == null) {
			return list;
		}
		
		List<Notification> notification = notificationRepository.findByUserId(user.getId());
		
		// 반환 리스트 만들기
		for (Notification notifi : notification) {
			NotificationRes notificationRes = new NotificationRes();
			notificationRes.setQuestionId(notifi.getQuestion().getId());
			notificationRes.setCode(notifi.getCode().getCode());
			notificationRes.setTitle(notifi.getQuestion().getTitle());
			notificationRes.setRegDt(notifi.getRegDt());
			list.add(notificationRes);
		}
		
		// 최신순 정렬
		Collections.sort(list, new Comparator<NotificationRes>() {

			@Override
			public int compare(NotificationRes o1, NotificationRes o2) {
				return o2.getRegDt().compareTo(o1.getRegDt());
			}
		});
		
		return list;
	}
	
}
