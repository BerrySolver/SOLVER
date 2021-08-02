package com.solver.api.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QuestionReq")
public class QuestionReq {	
	@ApiModelProperty(name="질문 제목", example="질문1")
	private String title;
	
	@ApiModelProperty(name="질문 내용", example="내용1")
	private String content;
	
	@ApiModelProperty(name="대분류", example="091")
	private String mainCategory;
	
	@ApiModelProperty(name="소분류", example="911")
	private String subCategory;
	
	@ApiModelProperty(name="난이도", example="1")
	private int difficulty;
	
	@ApiModelProperty(name="만료 시간", example="2021-08-01")
	private Date expirationTime;
}
