package com.solver.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.response.MessageListRes;
import com.solver.common.auth.KakaoUtil;
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
	public MessageListRes getSendMessageList(String accessToken) {
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		User user = UserRepository.findByKakaoId(kakaoId).orElse(null);
		
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
	public MessageListRes getReceiveMessageList(String accessToken) {
		String token = accessToken.split(" ")[1];
		
		Long kakaoId = kakaoUtil.getKakaoUserIdByToken(token);
		
		User user = UserRepository.findByKakaoId(kakaoId).orElse(null);
		
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
