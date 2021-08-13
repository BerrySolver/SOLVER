package com.solver.api.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

// 화상요청 메시지 생성 시
@Getter
@Setter
@ApiModel("MessageConferenceCreatePostReq")
public class MessageConferenceCreatePostReq {
	
	// 답변 ID
	@ApiModelProperty(name="답변 ID")
	private String answerId;
	
	// 시간
	@ApiModelProperty(name="요청 시간")
	private String regDt;
	
	// 답변 타입
	@ApiModelProperty(name="답변 ID")
	private String type;
	
	// 답변자 ID
	@ApiModelProperty(name="답변자 ID")
	private String answerUserId;
}
