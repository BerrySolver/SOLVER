package com.solver.api.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//전체 질문 조회를 위해 사용자에게 받는 입력값들
@Getter
@Setter
@ApiModel("QuestionGetListReq")
public class QuestionGetListReq {
	// 대분류
	@ApiModelProperty(name="mainCategory", example="091")
	private String mainCategory;
	
	// 소분류
	@ApiModelProperty(name="subCategory", example="911")
	private String subCategory;
	
	// 검색어
	@ApiModelProperty(name="query", example="질문")
	private String query;
	
	// 해시태그
	@ApiModelProperty(name="hashtag")
	private List<String> hashtag;
	
	// 난이도
	@ApiModelProperty(name="difficulty", example="0")
	private int difficulty;
	
	// 질문 상태
	@ApiModelProperty(name="type", example="all")
	private String type;
	
	// 정렬 기준
	@ApiModelProperty(name="mode", example="releaseDesc")
	private String mode;
	
	@ApiModelProperty(name="offset", example="1")
	private int offset;
	
	@ApiModelProperty(name="limit", example="10")
	private int limit;
	
}
