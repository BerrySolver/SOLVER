package com.solver.api.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("AnswerCreateGetReq")
public class AnswerCreateGetReq {
	@ApiModelProperty(name="content", example="test Content")
	private String content;
}
