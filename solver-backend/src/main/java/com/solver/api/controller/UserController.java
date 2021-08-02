package com.solver.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.service.UserService;
import com.solver.common.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true", allowedHeaders="*",
methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS})

@Api(value="유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@Autowired
	UserService	userService;
	
	@GetMapping("/signup/check-nickname")
	@ApiOperation(value = "닉네임 중복 체크", notes = "회원가입시 닉네임중복 체크") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "사용 가능한 닉네임입니다"),
        @ApiResponse(code = 409, message = "이미 사용중인 닉네임입니다")
    })
	public ResponseEntity<? extends BaseResponse> checkNickname(
			@RequestParam @ApiParam(value="닉네임", required=true) String nickname)
	{
		if(userService.checkNickname(nickname).orElse(null) == null)
			return ResponseEntity.status(200).body(BaseResponse.of(200, "사용 가능한 닉네임입니다"));
		
		return ResponseEntity.status(409).body(BaseResponse.of(409, "이미 사용중인 닉네임입니다"));
		
	}
	
	
}
