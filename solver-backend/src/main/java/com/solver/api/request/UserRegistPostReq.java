package com.solver.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserRegistPostReq")
public class UserRegistPostReq {
	@ApiModelProperty(name="로그인 ID", example="textId")
	private String loginId;
	
	@ApiModelProperty(name="유저 닉네임", example="nadang")
	private String nickname;
	
	@ApiModelProperty(name="비밀번호", example="1q2w3e4r!@")
	private String password;
	
	@ApiModelProperty(name="화상이용 가능 시간", example="\"t0000\": true, \"t0030\": true")
	private String possibleTime;
	
	@ApiModelProperty(name="가입 유형", example="001")
	private String type;
}
