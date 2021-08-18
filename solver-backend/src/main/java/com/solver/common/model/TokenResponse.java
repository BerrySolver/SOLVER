package com.solver.common.model;

import lombok.Data;

@Data
public class TokenResponse {
	Long kakaoId;
	String accessToken;
	String profileUrl;
}
