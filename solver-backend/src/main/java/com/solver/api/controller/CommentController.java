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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.CommentCreatePostReq;
import com.solver.api.request.CommentUpdatePatchReq;
import com.solver.api.response.CommentListRes;
import com.solver.api.service.CommentService;
import com.solver.common.model.BaseResponse;
import com.solver.db.entity.comment.Comment;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
	
	/* 댓글 삭제 */
	@DeleteMapping("/{commentId}")
	@ApiOperation(value = "댓글 삭제", notes = "내 댓글 삭제") 
    @ApiResponses({
        @ApiResponse(code = 204, message = "댓글 삭제 성공"),
        @ApiResponse(code = 409, message = "댓글 삭제 실패")
    })
	public ResponseEntity<? extends BaseResponse> deleteComment(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable @ApiParam(value="삭제할 댓글 ID", required=true) String commentId
			) 
	{
		
		boolean isSuccess = commentService.deleteComment(accessToken, commentId);
		
		if(!isSuccess)
			return ResponseEntity.status(409).body(BaseResponse.of(409, "댓글 삭제 실패"));
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "댓글 삭제 성공"));
	}
	
	/* 댓글 수정 */
	@PatchMapping("/{commentId}")
	@ApiOperation(value = "댓글 수정", notes = "내 댓글 수정") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "답변 댓글 성공"),
        @ApiResponse(code = 409, message = "답변 댓글 실패")
    })
	public ResponseEntity<? extends BaseResponse> updateComment(
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable @ApiParam(value="변경할 댓글 ID", required=true) String commentId,
			@RequestBody @ApiParam(value="변경할 댓글 내용", required=true) CommentUpdatePatchReq commentUpdatePatchReq
			) 
	{
		
		boolean isSuccess = commentService.updateComment(accessToken, commentId, commentUpdatePatchReq);
		
		if(!isSuccess)
			return ResponseEntity.status(409).body(BaseResponse.of(409, "답변 댓글 실패"));
		
		return ResponseEntity.status(204).body(BaseResponse.of(201, "답변 댓글 성공"));
	}
	
	/* 답변 목록 조회 */
	@GetMapping("/{answerId}")
	@ApiOperation(value = "댓글 목록 조회", notes = "답변에 대한 댓글 목록 조회") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "답변에 대한 댓글 목록 조회 성공"),
        @ApiResponse(code = 409, message = "답변에 대한 댓글 목록 조회 성공")
    })
	public ResponseEntity<? extends BaseResponse> getCommentList(
			@PathVariable @ApiParam(value="답변을 조회할 질문 ID", required=true) String answerId
			) 
	{
		List<Comment> commentList = commentService.getCommentList(answerId);
		
		if(commentList == null) {
			return ResponseEntity.status(409).body(CommentListRes.of(409, "답변 목록 조회 실패"));
		}
		
		return ResponseEntity.status(200).body(CommentListRes.of(200, "답변 목록 조회 성공", commentList));
	}
}
