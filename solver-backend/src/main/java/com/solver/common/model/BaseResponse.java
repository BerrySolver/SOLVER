package com.solver.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 서버 요청에대한 기본 응답값(바디) 정의.
 */
@Getter
@Setter
@ApiModel("BaseResponse")
public class BaseResponse {
	@ApiModelProperty(name="응답 메시지")
	String message = null;
	@ApiModelProperty(name="응답 코드")
	Integer statusCode = null;
	
	public BaseResponse() {}
	
	public BaseResponse(Integer statusCode){
		this.statusCode = statusCode;
	}
	
	public BaseResponse(Integer statusCode, String message){
		this.statusCode = statusCode;
		this.message = message;
	}
	
	public static BaseResponse of(Integer statusCode, String message) {
		BaseResponse body = new BaseResponse();
		body.message = message;
		body.statusCode = statusCode;
		return body;
	}
}
