package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//질문 수정 시 사용자에게 받을 입력값들
@Getter
@Setter
@ApiModel("QuestionPatchReq")
public class QuestionPatchReq {
	// 제목
	@ApiModelProperty(name="질문 제목", example="제목 수정")
	private String title;
	
	// 내용
	@ApiModelProperty(name="질문 내용", example="내용 수정")
	private String content;
	
	// 대분류
	@ApiModelProperty(name="대분류", example="092")
	private String mainCategory;
	
	// 소분류
	@ApiModelProperty(name="소분류", example="921")
	private String subCategory;
	
	// 난이도
	@ApiModelProperty(name="난이도", example="2")
	private int difficulty;
}
