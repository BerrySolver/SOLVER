package com.solver.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.QuestionGetListReq;
import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.api.response.QuestionListRes;
import com.solver.api.response.QuestionRes;
import com.solver.api.service.QuestionService;
import com.solver.api.service.UserService;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.BaseResponse;
import com.solver.db.entity.question.Question;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value="질문 API", tags = {"Qeustion"})
@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	@GetMapping()
	@ApiOperation(value = "질문 목록", notes = "질문 목록을 조회하는 API") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "질문 목록을 성공적으로 조회했습니다."),
        @ApiResponse(code = 400, message = "질문 목록 조회에 실패했습니다.")
    })
	public ResponseEntity<? extends BaseResponse> getQuestionList(
			@ModelAttribute QuestionGetListReq questionGetListReq)
	{	
		List<Question> questionList;
		System.out.println(questionGetListReq.getMainCategory());
		try {
			questionList = questionService.getQuestionList(questionGetListReq);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(QuestionListRes.of(400, "질문 목록 조회에 실패했습니다."));
		}
		
		return ResponseEntity.status(200).body(QuestionListRes.of(200, "질문 목록을 성공적으로 조회했습니다.", questionList));
	}
	
	@PostMapping()
	@ApiOperation(value = "질문 생성", notes = "질문을 생성하는 API") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "질문을 성공적으로 생성했습니다."),
        @ApiResponse(code = 400, message = "질문 생성에 실패했습니다.")
    })
	public ResponseEntity<? extends BaseResponse> createQuestion(
			@RequestBody @ApiParam(value="질문 정보", required=true) QuestionPostReq questionPostReq,
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		// accessToken은 'Bearer token' 형태로 넘어오므로 처리가 필요하다.
		String token = accessToken.split(" ")[1];
		Question question = questionService.createQuestion(questionPostReq, token);
		if (question == null) {
			return ResponseEntity.status(400).body(QuestionRes.of(400, "질문 생성에 실패했습니다."));
		}
		
		return ResponseEntity.status(201).body(QuestionRes.of(201, "질문을 성공적으로 생성했습니다."));
	}
	
	@GetMapping("/{questionId}")
	@ApiOperation(value = "질문 상세조회", notes = "질문 상세내용을 조회하는 API") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "질문을 성공적으로 조회했습니다."),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
    })
	public ResponseEntity<? extends BaseResponse> getQuestionDetail(
			@PathVariable @ApiParam(value="질문 Id", required=true) String questionId)
	{
		Optional<Question> question = questionService.getById(questionId);
		if (question == null) {
			return ResponseEntity.status(404).body(QuestionRes.of(404, "존재하지 않는 질문입니다."));
		}
		System.out.println(question.get().getAnswer());
		
		return ResponseEntity.status(200).body(QuestionRes.of(200, "질문을 성공적으로 조회했습니다.", question.get()));
	}
	
	@PatchMapping("/{questionId}")
	@ApiOperation(value = "질문 수정", notes = "질문 수정 API") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "질문을 성공적으로 수정했습니다."),
        @ApiResponse(code = 403, message = "권한이 없습니다."),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
    })
	public ResponseEntity<? extends BaseResponse> updateQuestion(
			@PathVariable @ApiParam(value="질문 Id", required=true) String questionId,
			@RequestBody @ApiParam(value="수정 내용", required=true) QuestionPatchReq questionPatchReq,
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		String token = accessToken.split(" ")[1];
		Optional<Question> optionalQuestion = questionService.getById(questionId);
		if (optionalQuestion == null) {
			return ResponseEntity.status(404).body(QuestionRes.of(404, "존재하지 않는 질문입니다."));
		}
		
		Question question = optionalQuestion.get();		
		question = questionService.updateQuestion(questionPatchReq, optionalQuestion.get(), token);
		
		if (question == null) {
			return ResponseEntity.status(403).body(QuestionRes.of(403, "권한이 없습니다."));
		}
		
		return ResponseEntity.status(201).body(QuestionRes.of(200, "질문을 성공적으로 수정했습니다."));
	}
	
	@DeleteMapping("/{questionId}")
	@ApiOperation(value = "질문 삭제", notes = "질문 삭제 API") 
    @ApiResponses({
        @ApiResponse(code = 204, message = "질문을 성공적으로 삭제했습니다."),
        @ApiResponse(code = 403, message = "권한이 없습니다."),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
    })
	public ResponseEntity<? extends BaseResponse> deleteQuestion(
			@PathVariable @ApiParam(value="질문 Id", required=true) String questionId,
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		String token = accessToken.split(" ")[1];
		Optional<Question> question = questionService.getById(questionId);
		if (question == null) {
			return ResponseEntity.status(404).body(QuestionRes.of(404, "존재하지 않는 질문입니다."));
		}
		
		try {
			questionService.deleteQuestion(question.get(), token);
		} catch (AccessDeniedException e) {
			return ResponseEntity.status(403).body(QuestionRes.of(403, "권한이 없습니다."));
		}
		
		return ResponseEntity.status(204).body(QuestionRes.of(204, "질문을 성공적으로 삭제했습니다."));
	}
}
