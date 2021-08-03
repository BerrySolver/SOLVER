package com.solver.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.ProfileUpdatePatchReq;
import com.solver.api.service.UserService;
import com.solver.common.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value="유저 API", tags = {"Profile"})
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
	@Autowired
	UserService	userService;
	
	@GetMapping("/{nickname}")
	@ApiOperation(value = "회원 프로필 정보", notes = "회원 마이페이지 기본 정보 조회") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "정보를 가져오는데 성공하였습니다"),
        @ApiResponse(code = 409, message = "정보를 가져오는데 실패하였습니다")
    })
	public ResponseEntity<? extends BaseResponse> deleteUser(@PathVariable String nickname)
	{
		userService.getProfileInfo(nickname);
		
		return ResponseEntity.status(201).body(BaseResponse.of(200, "정보를 가져오는데 성공하였습니다"));
	}
	
	@PatchMapping()
	@ApiOperation(value = "회원 프로필 정보 수정(시간제외)", notes = "회원 마이페이지 기본 정보 수정") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "프로필 정보 수정에 성공하였습니다"),
        @ApiResponse(code = 409, message = "프로필 정보 수정에 실패하였습니다")
    })
	public ResponseEntity<? extends BaseResponse> updateProfile(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@RequestBody ProfileUpdatePatchReq profileUpdatePatchReq)
	{
		userService.updateProfile(profileUpdatePatchReq, accessToken);
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "프로필 정보 수정에 성공하였습니다"));
	}
}
