package com.solver.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.UserRegistPostReq;
import com.solver.api.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	UserService	userService;
	
	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "아이디, 패스워드, 닉네임과 화상 가능 시간을 입력해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "화원가입에 성공했습니다"),
        @ApiResponse(code = 409, message = "회원가입에 실패했습니다")
    })
	public ResponseEntity<Integer> registUser(
			@RequestBody @ApiParam(value="회원가입 정보", required=true) UserRegistPostReq userRegistPostReq)
	{
		userService.createUser(userRegistPostReq);
		
		return null;
		
	}
}
