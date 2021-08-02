package com.solver.api.response;

import com.solver.common.model.BaseResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserLoginRes extends BaseResponse{
	private String accessToken;
}
