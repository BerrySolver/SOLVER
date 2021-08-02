package com.solver.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.UserUpdatePatchReq;
import com.solver.api.service.UserService;
import com.solver.common.model.BaseResponse;
import com.solver.common.util.JwtTokenUtil;
import com.solver.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value="유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
	@Autowired
	UserService	userService;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@PatchMapping("/{nickname}")
	@ApiOperation(value = "회원 정보 수정", notes = "닉네임, 프로필 사진, 소개, 링크 수정 한다.") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "유저 정보 수정에 성공했습니다"),
        @ApiResponse(code = 409, message = "유저 정보 수정에 실패했습니다")
    })
	public ResponseEntity<BaseResponse> updateUser(
			@ApiIgnore @RequestHeader("Authorization") String jwt,
			@RequestBody @ApiParam(value="유저 수정 정보", required=true) UserUpdatePatchReq userUpdatePatchReq)
	{
		String token = jwt.split(" ")[1];
		
		String loginId = jwtTokenUtil.getLoginIdFromToken(token);
		
		userService.updateUser(userUpdatePatchReq, loginId);
		
		return ResponseEntity.status(200).body(BaseResponse.of(200, "회원가입이 완료되었습니다"));
		
	}
}
