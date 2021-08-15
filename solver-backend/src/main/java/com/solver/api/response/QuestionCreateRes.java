package com.solver.api.response;

import com.solver.common.model.BaseResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProfileRes")
public class QuestionCreateRes extends BaseResponse{
	@ApiModelProperty(name="질문 ID")
	String questionId;
}
