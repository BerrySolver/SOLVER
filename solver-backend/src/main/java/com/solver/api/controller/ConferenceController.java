package com.solver.api.controller;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.AnswerCreatePostReq;
import com.solver.api.service.ConferenceService;
import com.solver.common.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value="화상회의 API", tags = {"Conference"})
@RestController
@RequestMapping("/api/v1/conferences")
public class ConferenceController {

	@Autowired
	ConferenceService conferenceService;
	
	/* 질문 화상 회의 상태 변경 
	 * 
	 * 상태 변경은 public으로 변환만 하는 건가?
	 * */
	@PatchMapping(value="/{conferenceId}")
	@ApiOperation(value = "화상 회의 상태 변경", notes = "화상 회의 공개여부 상태 변경") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "상태 변경 성공"),
        @ApiResponse(code = 409, message = "상태 변경 실패")
    })
	public ResponseEntity<? extends BaseResponse> updateConference(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String conferenceId
			) 
	{
		int flag = conferenceService.updateConference(accessToken, conferenceId);
		
		if(flag != 3) {
			return ResponseEntity.status(409).body(BaseResponse.of(409, "상태 변경 실패"));
		}
		
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "상태 변경 성공"));
	}
}
