package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("SolverGetListReq")
public class SolverGetListReq {	
	@ApiModelProperty(name="mainCategory", example="091")
	private String mainCategory;
	
	@ApiModelProperty(name="subCategory", example="911")
	private String subCategory;

	@ApiModelProperty(name="query", example="키워드")
	private String query;
	
	@ApiModelProperty(name="mode", example="pointDesc")
	private String mode;
}
