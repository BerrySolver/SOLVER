package com.solver.api.response;

import java.util.Date;
import java.util.List;

import com.solver.common.model.BaseResponse;
import com.solver.db.entity.code.Code;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("NotificationResponse")
public class NotificationRes extends BaseResponse {

	@ApiModelProperty(name = "질문 ID")
	private String questionId;

	@ApiModelProperty(name = "알림 종류")
	private String code;

	@ApiModelProperty(name = "질문 제목")
	private String title;

	@ApiModelProperty(name = "알림 등록 시간")
	private Date regDt;

	public static NotificationRes of(Integer statusCode, String message, NotificationRes notificationRes) {
		NotificationRes res = notificationRes;
		res.setStatusCode(statusCode);
		res.setMessage(message);		

		return res;
	}
}
