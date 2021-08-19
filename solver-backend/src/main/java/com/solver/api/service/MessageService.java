package com.solver.api.service;

import java.text.ParseException;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.request.MessageConferenceCreatePostReq;
import com.solver.api.request.MessageConferenceResultPostReq;
import com.solver.api.response.MessageListRes;

public interface MessageService {

	MessageListRes getSendMessageList(String accessToken, HttpServletResponse response);

	MessageListRes getReceiveMessageList(String accessToken, HttpServletResponse response);

	void insertMessage(MessageConferenceCreatePostReq messagePostReq);

	void resultMessage(MessageConferenceResultPostReq messagePostReq) throws ParseException;
}
