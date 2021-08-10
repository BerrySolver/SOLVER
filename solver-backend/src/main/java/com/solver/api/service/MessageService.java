package com.solver.api.service;

import javax.servlet.http.HttpServletResponse;

import com.solver.api.response.MessageListRes;

public interface MessageService {

	MessageListRes getSendMessageList(String accessToken, HttpServletResponse response);

	MessageListRes getReceiveMessageList(String accessToken, HttpServletResponse response);

}
