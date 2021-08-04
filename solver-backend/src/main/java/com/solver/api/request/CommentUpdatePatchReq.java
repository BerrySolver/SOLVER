package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CommentCreatePostReq")
public class CommentUpdatePatchReq {
	@ApiModelProperty(name="content", example="update comment Content")
	private String content;
}
