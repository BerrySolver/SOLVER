package com.solver.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.UserLoginPostReq;
import com.solver.api.response.UserLoginRes;
import com.solver.api.service.UserService;
import com.solver.common.auth.TokenProvider;
import com.solver.common.util.JwtTokenUtil;
import com.solver.db.entity.Auth;
import com.solver.db.entity.User;
import com.solver.db.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="인증 API", tags = {"Auth"})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
//	private final TokenProvider tokenProvider;
//	private final AuthenticationManagerBuilder authenticationManagerBuilder;
//	
//	public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
//		this.tokenProvider = tokenProvider;
//		this.authenticationManagerBuilder = authenticationManagerBuilder;
//	}
	
	// 로그인. id와 password를 loginDto로 받음
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "아이디, 패스워드를 입력해 로그인") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "로그인에 성공했습니다"),
        @ApiResponse(code = 409, message = "로그인에 실패했습니다")
    })
	public ResponseEntity<UserLoginRes> loginUser( 
			@ApiParam(value="회원가입 정보", required=true) @RequestBody UserLoginPostReq userLoginPostReq) {

		Optional<User> user = userService.checkLoginId(userLoginPostReq.getLoginId());
		Optional<Auth> auth = userService.loginUser(userLoginPostReq);
		
		if(user.orElse(null) == null)
			return ResponseEntity.status(409).body(null);
		
		if(auth.orElse(null) == null)
			return ResponseEntity.status(409).body(null);
		
		String accessToken = userService.makeToken(user.get());
		//인증 정보를 기준으로 jwt 토큰을 생성
//		String accessJwt = jwtTokenUtil.createAccessToken(user.get());
//		String refreshJwt = jwtTokenUtil.createRefreshToken(user.get());
		
		UserLoginRes userLoginRes = UserLoginRes.builder().accessToken(accessToken).build();
		userLoginRes.setMessage("로그인에 성공했습니다");
		userLoginRes.setStatusCode(200);

		//jwt 토큰을 response body에 넣는 경우
		return ResponseEntity.status(200).body(userLoginRes);
	}
}
