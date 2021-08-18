package com.solver.api.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

// 화상요청 메시지 생성 시
@Getter
@Setter
@ApiModel("MessageConferenceResultPostReq")
public class MessageConferenceResultPostReq {
	
	// 메시지 ID
	@ApiModelProperty(name="답변 ID")
	private String messageId;
	
	// 답변 타입
	@ApiModelProperty(name="답변 종류")
	private String type;
	
	// 전달 내용
	@ApiModelProperty(name="답변 내용")
	private String content;	
}
