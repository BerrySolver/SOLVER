package com.solver.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.response.PointLogListRes;
import com.solver.api.response.PointLogRes;
import com.solver.api.service.MessageService;
import com.solver.api.service.PointLogService;
import com.solver.common.model.BaseResponse;
import com.solver.db.entity.user.PointLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin("*")

@Api(value="포인트 API", tags = {"Point"})
@RestController
@RequestMapping("/api/v1/points")
public class PointLogController {
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	PointLogService pointLogService;
	
	@GetMapping("/{nickname}")
	@ApiOperation(value = "포인트 이력 가져오기", notes = "포인트 이력 목록 조회") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "포인트 이력 목록 조회 성공"),
        @ApiResponse(code = 409, message = "포인트 이력 목록 조회 실패")
    })
	public ResponseEntity<? extends BaseResponse> getPointLogs(
			HttpServletResponse response, 
			@PathVariable String nickname,
			@ApiIgnore @RequestHeader("Authorization") String accessToken
	)
	{
		List<PointLogRes> list = pointLogService.getPointLogList(nickname);
		return ResponseEntity.status(200).body(PointLogListRes.of(200, "포인트 이력", list));
	}
}
