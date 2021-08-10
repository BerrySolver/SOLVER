package com.solver.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.response.MessageListRes;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.db.entity.user.Message;
import com.solver.db.entity.user.User;
import com.solver.db.repository.user.MessageRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UserRepository UserRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;

	@Override
	public MessageListRes getSendMessageList(String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		User user = UserRepository.findByKakaoId(kakaoId).orElse(null);
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		MessageListRes messageListRes = new MessageListRes();
		
		if(user == null) {
			messageListRes.setMessage("보낸 메시지 목록 조회 실패");
			messageListRes.setStatusCode(409);
			return messageListRes;
		}
		
		List<Message> messageList = messageRepository.findBySendUserId(user.getId());
		
		messageListRes.setMessageList(messageList);
		messageListRes.setMessage("보낸 메시지 목록 조회 성공");
		messageListRes.setStatusCode(200);
		
		return messageListRes;
	}
	
	@Override
	public MessageListRes getReceiveMessageList(String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
		
		Long kakaoId = tokenResponse.getKakaoId();
		
		User user = UserRepository.findByKakaoId(kakaoId).orElse(null);
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		MessageListRes messageListRes = new MessageListRes();
		
		if(user == null) {
			messageListRes.setMessage("받은 메시지 목록 조회 실패");
			messageListRes.setStatusCode(409);
			return messageListRes;
		}
		
		List<Message> messageList = messageRepository.findByReceiveUserId(user.getId());
		
		messageListRes.setMessageList(messageList);
		messageListRes.setMessage("받은 메시지 목록 조회 성공");
		messageListRes.setStatusCode(200);
		
		return messageListRes;
	}
}
