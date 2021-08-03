package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ProfilePossibleTimePatchReq")
public class ProfilePossibleTimePatchReq {
	@ApiModelProperty(name="categoryList", example="10:00|20:00")
	private String possibleTime;
}
