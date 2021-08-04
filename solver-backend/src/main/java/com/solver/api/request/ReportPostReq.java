package com.solver.api.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

// 신고 생성 시 사용자에게 받는 입력값들
@Getter
@Setter
@ApiModel("QuestionPostReq")
public class ReportPostReq {
	
	// 신고 대상 타입
	@ApiModelProperty(name="신고 대상", example="question")
	private String target;
	
	// 신고 대상 Id
	@ApiModelProperty(name="신고 대상 Id", example="BDZk2C590TTVK")
	private String targetId;
	
	// 신고 사유
	@ApiModelProperty(name="신고 사유", example="불건전합니다.")
	private String reason;
}
