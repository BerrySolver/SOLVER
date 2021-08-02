package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QuestionPatchReq")
public class QuestionPatchReq {
	@ApiModelProperty(name="질문 제목", example="제목 수정")
	private String title;
	
	@ApiModelProperty(name="질문 내용", example="내용 수정")
	private String content;
	
	@ApiModelProperty(name="난이도", example="2")
	private int difficulty;
}
