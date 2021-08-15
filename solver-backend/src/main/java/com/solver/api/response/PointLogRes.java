package com.solver.api.response;

import java.util.Date;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.user.PointLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PointLogResponse")
public class PointLogRes extends BaseResponse {

	@ApiModelProperty(name = "포인트 기록")
	private Date redDt;

	@ApiModelProperty(name = "포인트 코드")
	private String pointCode;

	@ApiModelProperty(name = "포인트 이름")
	private String pointName;

	@ApiModelProperty(name = "포인트 값")
	private int value;

	@ApiModelProperty(name = "포인트 종류")
	private String code;
	
	public static PointLogRes of(Integer statusCode, String message, PointLogRes pointLogRes) {
		PointLogRes res = pointLogRes;
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}
