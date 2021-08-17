package com.solver.api.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.UserRegistPostReq;
import com.solver.api.service.UserService;
import com.solver.common.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin("*")

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
	
	@PostMapping("/signup")
	@ApiOperation(value = "회원 가입", notes = "유저 회원가입") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "회원 가입에 성공했습니다"),
        @ApiResponse(code = 409, message = "회원 가입에 실패했습니다")
    })
	public ResponseEntity<? extends BaseResponse> signUp(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@RequestBody @ApiParam(value="회원 가입 정보", required=true) UserRegistPostReq userRegistPostReq)
	{
		userService.singUp(userRegistPostReq, accessToken, response);

		return ResponseEntity.status(201).body(BaseResponse.of(201, "회원 가입에 성공했습니다"));
	}
	
	
	@DeleteMapping()
	@ApiOperation(value = "회원 탈퇴", notes = "유저 회원탈퇴") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "회원 탈퇴가 완료되었습니다"),
        @ApiResponse(code = 409, message = "회원 탈퇴에 실패하였습니다")
    })
	public ResponseEntity<? extends BaseResponse> deleteUser(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		userService.deleteUser(accessToken, response);

		return ResponseEntity.status(201).body(BaseResponse.of(204, "회원 탈퇴가 완료되었습니다"));
	}
	
	@GetMapping("/nickname")
	@ApiOperation(value = "Test", notes = "111") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "222"),
        @ApiResponse(code = 409, message = "333")
    })
	public String getNickname(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{	
		String nickname = userService.getNickname(accessToken, response);

		return nickname;
	}
}
