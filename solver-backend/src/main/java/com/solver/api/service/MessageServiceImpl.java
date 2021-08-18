package com.solver.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.MessageConferenceCreatePostReq;
import com.solver.api.request.MessageConferenceResultPostReq;
import com.solver.api.response.MessageListRes;
import com.solver.api.response.MessageRes;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.conference.ConferenceLog;
import com.solver.db.entity.conference.ConferenceReservation;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.Message;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.conference.ConferenceLogRepository;
import com.solver.db.repository.conference.ConferenceReservationRepository;
import com.solver.db.repository.question.QuestionRepository;
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
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	CodeRepository codeRepository;
	
	@Autowired
	ConferenceReservationRepository conferenceReservationRepository;

	@Autowired
	QuestionRepository questionRepository;
	
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
		List<MessageRes> list = new ArrayList<MessageRes>();
		
		messageListRes.setMessageList(list);
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
		List<MessageRes> list = new ArrayList<MessageRes>();
		
		// 반환 리스트 만들기
		for (Message message : messageList) {
			MessageRes res = new MessageRes();	
			res.setMessageId(message.getId());
			res.setQuestionId(message.getQuestionId());
			res.setContent(message.getContent());
			res.setSendNickName(message.getSendUser().getNickname());
			res.setType(message.getCode().getCode());
			res.setRegDt(message.getRegDt());
			res.setChecked(message.isChecked());
			list.add(res);
		}
		
		// 최신순 정렬
		Collections.sort(list, new Comparator<MessageRes>() {

			@Override
			public int compare(MessageRes o1, MessageRes o2) {
				return o2.getRegDt().compareTo(o1.getRegDt());
			}
		});
		
		messageListRes.setMessageList(list);
		messageListRes.setMessage("받은 메시지 목록 조회 성공");
		messageListRes.setStatusCode(200);
		
		return messageListRes;
	}

	@Override
	public void insertMessage(MessageConferenceCreatePostReq messagePostReq) {		
		Optional<Answer> answer = answerRepository.findById(messagePostReq.getAnswerId());
		
		if (answer.orElse(null) != null) {
			Message message = new Message();
			message.setId(RandomIdUtil.makeRandomId(13));
			message.setSendUser(answer.get().getQuestion().getUser());
			message.setReceiveUser(answer.get().getUser());
			message.setQuestionId(answer.get().getQuestion().getId());
			Code code = codeRepository.findByCode(messagePostReq.getType());
			message.setCode(code);
			message.setRegDt(new Date(System.currentTimeMillis()));
			
			String content = "";
			if(code.getCode().equals("072")) {				
				// 화상요청의 내용은 시간
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date to = null;
				String time = null;
				try {
					to = transFormat.parse(messagePostReq.getRegDt());
					time = transFormat.format(to);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				content = time;
				message.setChecked(false);	
			}
					
			message.setContent(content);
			messageRepository.save(message);
		}
	}

	@Override
	public void resultMessage(MessageConferenceResultPostReq messagePostReq) throws ParseException {
		String type = messagePostReq.getType();
		
		Optional<Message> optionalMessage = messageRepository.findById(messagePostReq.getMessageId());
		Message message = optionalMessage.get();
		
		// 원래 질문을 다시 선택할 수 없도록 DB 갱신
		message.setChecked(true);
		messageRepository.save(message);
		
		if("073".equals(type)) {
			// 승인했을 때
			
			// 신청자에게 승인 안내문구 전달
			Message m = new Message();
			m.setId(RandomIdUtil.makeRandomId(13));
			m.setSendUser(message.getReceiveUser());
			m.setReceiveUser(message.getSendUser());
			m.setQuestionId(message.getQuestionId());
			Code code = codeRepository.findByCode(type);
			m.setCode(code);
			m.setRegDt(new Date(System.currentTimeMillis()));
			m.setContent(messagePostReq.getContent());
			messageRepository.save(m); 
			
			// 승인자에게 성사 안내문구 전달
			m.setId(RandomIdUtil.makeRandomId(13));
			m.setSendUser(message.getSendUser());
			m.setReceiveUser(message.getReceiveUser());
			m.setContent("성사되었습니다.");
			messageRepository.save(m);
			
			// 컨퍼런스 예약은 양쪽다 남기기
			ConferenceReservation conferenceReservation = new ConferenceReservation();			
			Optional<Question> q = questionRepository.findById(message.getQuestionId());
			conferenceReservation.setQuestion(q.get());
			
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date to = null;
			String time = null;
			try {
				to = transFormat.parse(message.getContent());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			conferenceReservation.setStartDt(to);
			
			conferenceReservation.setId(RandomIdUtil.makeRandomId(13));
			conferenceReservation.setUser(message.getSendUser());
			conferenceReservationRepository.save(conferenceReservation);
			
			conferenceReservation.setId(RandomIdUtil.makeRandomId(13));
			conferenceReservation.setUser(message.getReceiveUser());
			conferenceReservationRepository.save(conferenceReservation);
			
			// 컨퍼런스 메시지 남기기
			Date newDate = new Date(to.getTime()-(1000*60*30));
			m = new Message();
			m.setId(RandomIdUtil.makeRandomId(13));
			m.setSendUser(message.getReceiveUser());
			m.setReceiveUser(message.getSendUser());
			m.setQuestionId(message.getQuestionId());
			code = codeRepository.findByCode("075");
			m.setCode(code);
			m.setRegDt(newDate);
			m.setContent("곧 화상이 시작됩니다.");
			messageRepository.save(m); 
			
			m.setId(RandomIdUtil.makeRandomId(13));
			m.setSendUser(message.getSendUser());
			m.setReceiveUser(message.getReceiveUser());
			messageRepository.save(m);		
			
		}else if("074".equals(type)) {
			// 신청한 사람에게 사유와 함께 전달
			Message m = new Message();
			m.setId(RandomIdUtil.makeRandomId(13));
			m.setSendUser(message.getReceiveUser());
			m.setReceiveUser(message.getSendUser());
			m.setQuestionId(message.getQuestionId());
			Code code = codeRepository.findByCode(type);
			m.setCode(code);
			m.setRegDt(new Date(System.currentTimeMillis()));
			m.setContent(messagePostReq.getContent());
			messageRepository.save(m);
		}
		
	}
}
