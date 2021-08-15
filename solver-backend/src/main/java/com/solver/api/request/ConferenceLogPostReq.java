package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ConferenceLogPostReq")
public class ConferenceLogPostReq {
	@ApiModelProperty(name="type", example="030")
	private String type;
}