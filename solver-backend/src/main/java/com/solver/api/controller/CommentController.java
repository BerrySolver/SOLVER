package com.solver.api.controller;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.CommentCreatePostReq;
import com.solver.api.service.CommentService;
import com.solver.common.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true", allowedHeaders="*",
methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS})

@Api(value="댓글 API", tags = {"Comment"})
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	
	@Autowired
	CommentService commentService;
	 
	/* 댓글 등록 */
	@PostMapping("/{answerId}")
	@ApiOperation(value = "댓글 작성", notes = "질문에 대한 댓글 작성") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "댓글 작성 성공"),
        @ApiResponse(code = 409, message = "댓글 작성 실패")
    })
	public ResponseEntity<? extends BaseResponse> createComment(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String answerId,
			CommentCreatePostReq commentCreatePostReq
			) throws ParseException 
	{
		commentService.createComment(accessToken, commentCreatePostReq, answerId);
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "댓글 작성에 성공했습니다"));
	}
	
//	/* 답변 삭제 */
//	@DeleteMapping("/{answerId}")
//	@ApiOperation(value = "답변 삭제", notes = "내 답변 삭제") 
//    @ApiResponses({
//        @ApiResponse(code = 204, message = "답변 삭제에 성공했습니다"),
//        @ApiResponse(code = 409, message = "답변 삭제에 실패했습니다")
//    })
//	public ResponseEntity<? extends BaseResponse> deleteComment(
//			@ApiIgnore @RequestHeader("Authorization") String accessToken,
//			@PathVariable @ApiParam(value="삭제할 답변 ID", required=true) String answerId
//			) 
//	{
//		
//		boolean isSuccess = answerService.deleteAnswer(accessToken, answerId);
//		
//		if(!isSuccess)
//			return ResponseEntity.status(409).body(BaseResponse.of(409, "답변 삭제에 실패했습니다"));
//		
//		return ResponseEntity.status(204).body(BaseResponse.of(204, "답변 삭제에 성공했습니다"));
//	}
//	
//	/* 답변 수정 */
//	@PatchMapping("/{answerId}")
//	@ApiOperation(value = "답변 수정", notes = "내 답변 수정") 
//    @ApiResponses({
//        @ApiResponse(code = 201, message = "답변 수정에 성공했습니다"),
//        @ApiResponse(code = 409, message = "답변 수정에 실패했습니다")
//    })
//	public ResponseEntity<? extends BaseResponse> updateComment(
//			@ApiIgnore @RequestHeader("Authorization") String accessToken,
//			@PathVariable @ApiParam(value="변경할 답변 ID", required=true) String answerId,
//			@RequestBody @ApiParam(value="변경할 답변 내용", required=true) AnswerUpdatePatchReq answerUpdatePatchReq
//			) 
//	{
//		
//		boolean isSuccess = answerService.updateAnswer(accessToken, answerId, answerUpdatePatchReq);
//		
//		if(!isSuccess)
//			return ResponseEntity.status(409).body(BaseResponse.of(409, "답변 수정에 실패했습니다"));
//		
//		return ResponseEntity.status(204).body(BaseResponse.of(201, "답변 수정에 성공했습니다"));
//	}
//	
//	/* 답변 목록 조회 */
//	@GetMapping("/{questionId}")
//	@ApiOperation(value = "답변 목록 조회", notes = "질문에 대한 답변 목록 조회") 
//    @ApiResponses({
//        @ApiResponse(code = 200, message = "답변 목록 조회 성공"),
//        @ApiResponse(code = 409, message = "답변 목록 조회 실패")
//    })
//	public ResponseEntity<? extends BaseResponse> getCommentList(
//			@PathVariable @ApiParam(value="답변을 조회할 질문 ID", required=true) String questionId
//			) 
//	{
//		List<Answer> answerList = answerService.getAnswerList(questionId);
//		
//		if(answerList == null) {
//			return ResponseEntity.status(409).body(AnswerListRes.of(409, "답변 목록 조회 실패"));
//		}
//		
//		return ResponseEntity.status(409).body(AnswerListRes.of(200, "답변 목록 조회 성공", answerList));
//	}
}
