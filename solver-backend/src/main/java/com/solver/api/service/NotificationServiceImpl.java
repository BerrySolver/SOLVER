package com.solver.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.response.NotificationListRes;
import com.solver.common.auth.KakaoUtil;
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
	public NotificationListRes getAllNotificationList(String accessToken) {
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);
		
		NotificationListRes notificationListRes = new NotificationListRes(); 
		
		if(user == null) {
			notificationListRes.setMessage("전체 알림 목록 조회 실패");
			notificationListRes.setStatusCode(409);
			return notificationListRes;
		}
		
		List<Notification> notificationList = notificationRepository.findByUserId(user.getId());
		notificationListRes.setMessage("전체 알림 목록 조회 성공");
		notificationListRes.setStatusCode(200);
		notificationListRes.setNotificationList(notificationList);
		
		return notificationListRes;
	}

	@Override
	public NotificationListRes getVideoNotificationList(String accessToken) {
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		User user = userRepository.findByKakaoId(kakaoId).orElse(null);
		
		NotificationListRes notificationListRes = new NotificationListRes(); 
		
		if(user == null) {
			notificationListRes.setMessage("화상 알림 목록 조회 실패");
			notificationListRes.setStatusCode(409);
			return notificationListRes;
		}
		
		Code code = new Code();
		
		code.setCode("061");
		
		List<Notification> notificationList = notificationRepository.findByUserIdAndCode(user.getId(), code);
		notificationListRes.setMessage("화상 알림 목록 조회 성공");
		notificationListRes.setStatusCode(200);
		notificationListRes.setNotificationList(notificationList);
		
		return notificationListRes;
	}
	
	
}
