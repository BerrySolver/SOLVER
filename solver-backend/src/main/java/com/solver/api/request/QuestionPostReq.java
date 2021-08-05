package com.solver.api.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//질문 생성 시 사용자에게 받는 입력값들
@Getter
@Setter
@ApiModel("QuestionPostReq")
public class QuestionPostReq {
	// 제목
	@ApiModelProperty(name="질문 제목", example="질문1")
	private String title;
	
	// 내용
	@ApiModelProperty(name="질문 내용", example="내용1")
	private String content;
	
	// 대분류
	@ApiModelProperty(name="대분류", example="091")
	private String mainCategory;
	
	// 소분류
	@ApiModelProperty(name="소분류", example="911")
	private String subCategory;
	
	// 난이도
	@ApiModelProperty(name="난이도", example="1")
	private int difficulty;
	
	// 만료 시간
	@ApiModelProperty(name="만료 시간", example="2021-08-01")
	private Date expirationTime;
}
