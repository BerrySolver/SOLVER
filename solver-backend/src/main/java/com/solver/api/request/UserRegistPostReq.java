package com.solver.api.request;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserRegistPostReq")
public class UserRegistPostReq {
	@ApiModelProperty(name="유저 닉네임", example="nadang")
	private String nickname;
	
	@ApiModelProperty(name="평일 화상이용 가능 시간", example="00:00|00:30")
	private String weekdayTime;
	
	@ApiModelProperty(name="평일 화상이용 가능 시간", example="10:00|20:30")
	private String weekendTime;
	
	@ApiModelProperty(name="평일 화상이용 가능 시간", example="10:00|20:30")
	List<String> selectedCode;
	
	@ApiModelProperty(name="가입 유형", example="001")
	private String type;
}
