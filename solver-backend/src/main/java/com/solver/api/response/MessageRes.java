package com.solver.api.response;

import java.util.Date;
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
	
	@ApiModelProperty(name="메시지 ID")
	private String messageId;	

	@ApiModelProperty(name = "질문 ID")
	private String questionId;

	@ApiModelProperty(name = "알림 내용")
	private String content;

	@ApiModelProperty(name = "보낸 사람 닉네임")
	private String sendNickName;

	@ApiModelProperty(name = "알림 종류")
	private String type;

	@ApiModelProperty(name = "알림 등록 시간")
	private Date regDt;
	
	// 알림 처리 유무
	@ApiModelProperty(name="알림 처리 유무")
	private boolean checked;

	public static MessageRes of(Integer statusCode, String message, MessageRes messageRes) {
		MessageRes res = messageRes;
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}
