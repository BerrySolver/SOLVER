package com.solver.api.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

// 화상요청 메시지 생성 시
@Getter
@Setter
@ApiModel("PaySolverReq")
public class PaySolverReq {
	
	// 답변 ID
	@ApiModelProperty(name="요청자 닉네임")
	private String userNick;
	
	// 시작 시간
	@ApiModelProperty(name="시작 시간")
	private String startRegDt;
	
	// 종료 시간
	@ApiModelProperty(name="종료 시작")
	private String endRegDt;
}
