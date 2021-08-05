package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AnswerCreateGetReq")
public class AnswerUpdatePatchReq {
	@ApiModelProperty(name="content", example="답변 수정 내용")
	private String content;
}
