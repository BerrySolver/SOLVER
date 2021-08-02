package com.solver.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.QuestionReq;
import com.solver.api.response.QuestionRes;
import com.solver.api.service.QuestionService;
import com.solver.common.model.BaseResponse;
import com.solver.db.entity.question.Question;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="질문 API", tags = {"Qeustion"})
@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {
	@Autowired
	QuestionService questionSevice;
	
	@PostMapping()
	@ApiOperation(value = "질문 생성", notes = "질문을 생성하는 API") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "질문을 성공적으로 생성했습니다."),
        @ApiResponse(code = 400, message = "질문 생성에 실패했습니다.")
    })
	public ResponseEntity<? extends BaseResponse> createQuestion(
			@RequestBody @ApiParam(value="질문 정보", required=true) QuestionReq questionReq)
	{
		Question question = questionSevice.createQuestion(questionReq);
		if (question == null) {
			return ResponseEntity.status(400).body(QuestionRes.of(400, "질문 생성에 실패했습니다."));
		}
		
		return ResponseEntity.status(201).body(QuestionRes.of(201, "질문을 성공적으로 생성했습니다.", question));
		
	}
	
	@GetMapping("/{questionId}")
	@ApiOperation(value = "질문 상세조회", notes = "질문 상세내용을 조회하는 API") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "질문을 성공적으로 조회했습니다."),
        @ApiResponse(code = 404, message = "질문 조회에 실패했습니다.")
    })
	public ResponseEntity<? extends BaseResponse> getQuestionDetail(
			@PathVariable @ApiParam(value="질문 Id", required=true) String questionId)
	{
		Optional<Question> question = questionSevice.getById(questionId);
		if (question == null) {
			return ResponseEntity.status(404).body(QuestionRes.of(404, "질문 조회에 실패했습니다."));
		}
		
		return ResponseEntity.status(200).body(QuestionRes.of(200, "질문을 성공적으로 조회했습니다.", question.get()));
		
	}
}
