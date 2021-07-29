package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserLoginPostReq")
public class UserLoginPostReq {
	@ApiModelProperty(name="로그인 ID", example="textId")
	private String loginId;
	@ApiModelProperty(name="Password", example="$2a$10$bnOHujpjdheYw0sqkm1dsOjVoPjFo2MmJSvFh3vghk4W2qIZ2U85y")
	private String password;
}
