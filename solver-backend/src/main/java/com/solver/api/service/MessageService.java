package com.solver.api.service;

import com.solver.api.response.MessageListRes;

public interface MessageService {

	MessageListRes getSendMessageList(String accessToken);

	MessageListRes getReceiveMessageList(String accessToken);

}
