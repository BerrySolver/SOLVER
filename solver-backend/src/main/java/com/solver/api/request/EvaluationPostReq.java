package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EvaluationPostReq")
public class EvaluationPostReq {
	@ApiModelProperty(name="score", example="8")
	private int score;
	
	@ApiModelProperty(name="reason", example="그냥 좋다")
	private String reason;
	
	@ApiModelProperty(name="questionId", example="")
	private String questionId;
}
