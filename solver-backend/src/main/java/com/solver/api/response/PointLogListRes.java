package com.solver.api.response;

import java.util.Date;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.code.PointCode;
import com.solver.db.entity.user.PointLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PointLogListResponse")
public class PointLogListRes extends BaseResponse {
	private List<PointLogRes> list;

	public static PointLogListRes of(Integer statusCode, String message, List<PointLogRes> list) {
		PointLogListRes res = new PointLogListRes();
		res.setList(list);
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}
