package com.solver.api.controller;

import javax.websocket.server.PathParam;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.AnswerCreateGetReq;
import com.solver.api.response.UserLoginRes;
import com.solver.api.service.AnswerService;
import com.solver.common.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

	/* 답변 등록 */
	@GetMapping(value="/{questionId}")
	@ApiOperation(value = "답변 작성", notes = "질문에 대한 답변 작성") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "답변 작성에 성공했습니다"),
        @ApiResponse(code = 409, message = "답변 작성에 실패했습니다")
    })
	public ResponseEntity<? extends BaseResponse> createAnswer(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String questionId,
			AnswerCreateGetReq answerCreateGetReq
			) throws ParseException {
		
		answerService.createAnswer(accessToken, answerCreateGetReq, questionId);
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "답변 작성에 성공했습니다"));
	}
	
	/* 답변 삭제 */
	@DeleteMapping("/{answerId}")
	@ApiOperation(value = "답변 삭제", notes = "내 답변 삭제") 
    @ApiResponses({
        @ApiResponse(code = 204, message = "답변 삭제에 성공했습니다"),
        @ApiResponse(code = 409, message = "답변 삭제에 실패했습니다")
    })
	public ResponseEntity<? extends BaseResponse> deleteAnswer(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable @ApiParam(value="삭제할 답변 ID", required=true) String answerId
			) {
		
		boolean isSuccess = answerService.deleteAnswer(accessToken, answerId);
		
		if(!isSuccess)
			return ResponseEntity.status(409).body(BaseResponse.of(204, "답변 삭제에 실패했습니다"));
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "답변 삭제에 성공했습니다"));
	}
}
