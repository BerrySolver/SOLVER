package com.solver.api.response;

import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.user.Message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("MessageRes")
public class MessageRes extends BaseResponse{

	@ApiModelProperty(name = "질문 ID")
	private String questionId;

	@ApiModelProperty(name = "알림 내용")
	private String content;

	@ApiModelProperty(name = "보낸 사람 닉네임")
	private String sendNickName;

	@ApiModelProperty(name = "알림 종류")
	private String type;

	public static MessageRes of(Integer statusCode, String message, MessageRes messageRes) {
		MessageRes res = messageRes;
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}
