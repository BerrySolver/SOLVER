package com.solver.api.controller;

import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.AnswerCreatePostReq;
import com.solver.api.request.AnswerUpdatePatchReq;
import com.solver.api.response.AnswerListRes;
import com.solver.api.service.AnswerService;
import com.solver.api.service.FavoriteAnswerService;
import com.solver.common.model.BaseResponse;
import com.solver.db.entity.answer.Answer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true", allowedHeaders="*",
methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS})

@Api(value="답변 API", tags = {"Answer"})
@RestController
@RequestMapping("/api/v1/answers")
public class AnswerController {
	
	@Autowired
	AnswerService answerService;
	
	@Autowired
	FavoriteAnswerService favoriteAnswerService;

	/* 답변 등록 */
	@PostMapping(value="/{questionId}")
	@ApiOperation(value = "답변 작성", notes = "질문에 대한 답변 작성") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "답변 작성에 성공했습니다"),
        @ApiResponse(code = 409, message = "답변 작성에 실패했습니다")
    })
	public ResponseEntity<? extends BaseResponse> createAnswer(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String questionId,
			AnswerCreatePostReq answerCreatePostReq
			) throws ParseException 
	{
		answerService.createAnswer(accessToken, answerCreatePostReq, questionId);
		
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
			) 
	{
		
		boolean isSuccess = answerService.deleteAnswer(accessToken, answerId);
		
		if(!isSuccess)
			return ResponseEntity.status(409).body(BaseResponse.of(409, "답변 삭제에 실패했습니다"));
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "답변 삭제에 성공했습니다"));
	}
	
	/* 답변 수정 */
	@PatchMapping("/{answerId}")
	@ApiOperation(value = "답변 수정", notes = "내 답변 수정") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "답변 수정에 성공했습니다"),
        @ApiResponse(code = 409, message = "답변 수정에 실패했습니다")
    })
	public ResponseEntity<? extends BaseResponse> updateAnswer(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable @ApiParam(value="변경할 답변 ID", required=true) String answerId,
			@RequestBody @ApiParam(value="변경할 답변 내용", required=true) AnswerUpdatePatchReq answerUpdatePatchReq
			) 
	{
		
		boolean isSuccess = answerService.updateAnswer(accessToken, answerId, answerUpdatePatchReq);
		
		if(!isSuccess)
			return ResponseEntity.status(409).body(BaseResponse.of(409, "답변 수정에 실패했습니다"));
		
		return ResponseEntity.status(204).body(BaseResponse.of(201, "답변 수정에 성공했습니다"));
	}
	
	/* 답변 목록 조회 */
	@GetMapping("/{questionId}")
	@ApiOperation(value = "답변 목록 조회", notes = "질문에 대한 답변 목록 조회") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "답변 목록 조회 성공"),
        @ApiResponse(code = 409, message = "답변 목록 조회 실패")
    })
	public ResponseEntity<? extends BaseResponse> getAnswerList(
			@PathVariable @ApiParam(value="답변을 조회할 질문 ID", required=true) String questionId
			) 
	{
		List<Answer> answerList = answerService.getAnswerList(questionId);
		
		if(answerList == null) {
			return ResponseEntity.status(409).body(AnswerListRes.of(409, "답변 목록 조회 실패"));
		}
		
		return ResponseEntity.status(200).body(AnswerListRes.of(200, "답변 목록 조회 성공", answerList));
	}
	
	/* 답변 좋아요 추가 */
	@PostMapping("/{answerId}/recommend")
	@ApiOperation(value = "답변 좋아요 추가", notes = "사용자가 답변에 대해 좋아요 추가") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "답변 좋아요 추가 성공"),
        @ApiResponse(code = 409, message = "답변 좋아요 추가 실패")
    })
	public ResponseEntity<? extends BaseResponse> createFavoriteAnswer(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable @ApiParam(value="좋아요를 추가할 답변 ID", required=true) String answerId
			) 
	{
		int flag = favoriteAnswerService.createFavoriteAnswer(accessToken, answerId);
		
		if(flag != 3) {
			return ResponseEntity.status(409).body(BaseResponse.of(409, "답변 좋아요 추가 실패"));
		}
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "답변 좋아요 추가 성공"));
	}
	
	/* 답변 좋아요 삭제 */
	@DeleteMapping("/{answerId}/recommend")
	@ApiOperation(value = "답변 좋아요 삭제", notes = "사용자가 답변에 대해 좋아요 삭제") 
    @ApiResponses({
        @ApiResponse(code = 204, message = "답변 좋아요 삭제 성공"),
        @ApiResponse(code = 409, message = "답변 좋아요 삭제 실패")
    })
	public ResponseEntity<? extends BaseResponse> deleteFavoriteAnswer(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable @ApiParam(value="좋아요를 추가할 답변 질문 ID", required=true) String answerId
			) 
	{
		int flag = favoriteAnswerService.deleteFavoriteAnswer(accessToken, answerId);
		
		if(flag != 3) {
			return ResponseEntity.status(409).body(BaseResponse.of(409, "답변 좋아요 삭제 실패"));
		}
		
		System.out.println("!!!");
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "답변 좋아요 삭제 성공"));
	}
}
