package com.solver.api.request;

import java.util.List;

import com.solver.db.entity.code.Category;
import com.solver.db.entity.code.Code;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("QuestionGetListReq")
public class QuestionGetListReq {
	@ApiModelProperty(name="mainCategory", example="091")
	private String mainCategory;
	
	@ApiModelProperty(name="subCategory", example="911")
	private String subCategory;
	
	@ApiModelProperty(name="query", example="질문")
	private String query;
	
	@ApiModelProperty(name="hashtag")
	private List<String> hashtag;
	
	@ApiModelProperty(name="difficulty", example="0")
	private int difficulty;
	
	@ApiModelProperty(name="type", example="all")
	private String type;
	
	@ApiModelProperty(name="mode", example="releaseDesc")
	private String mode;
	
}
