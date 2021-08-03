package com.solver.api.controller;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.AnswerCreateGetReq;
import com.solver.api.response.UserLoginRes;
import com.solver.api.service.AnswerService;
import com.solver.common.auth.KakaoUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true", allowedHeaders="*",
methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS})

@Api(value="답변 API", tags = {"Answer"})
@RestController
@RequestMapping("/api/v1/answers")
public class AnswerController {
	
	@Autowired
	AnswerService answerService;

	// 로그인. id와 password를 loginDto로 받음
	@GetMapping(value="/{questionId}")
	@ApiOperation(value = "답변 작성", notes = "질문에 대한 답변 작성") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "로그인에 성공했습니다"),
        @ApiResponse(code = 409, message = "로그인에 실패했습니다")
    })
	public ResponseEntity<UserLoginRes> createAnswer(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String questionId,
			AnswerCreateGetReq answerCreateGetReq
			) throws ParseException {
		
		answerService.createAnswer(accessToken, answerCreateGetReq, questionId);
		
		return null;
	}
}
