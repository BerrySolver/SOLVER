package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.user.Message;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("MessageListRes")
public class MessageListRes extends BaseResponse{
	private List<Message> messageList;

}
