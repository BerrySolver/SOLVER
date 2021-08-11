package com.solver.api.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.QuestionGetListReq;
import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.api.response.QuestionCreateRes;
import com.solver.api.response.QuestionListRes;
import com.solver.api.response.QuestionMeRes;
import com.solver.api.response.QuestionRes;
import com.solver.api.service.BookmarkQuestionService;
import com.solver.api.service.ConferenceReservationService;
import com.solver.api.service.FavoriteQuestionService;
import com.solver.api.service.QuestionService;
import com.solver.api.service.UserService;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.BaseResponse;
import com.solver.db.entity.conference.ConferenceReservation;
import com.solver.db.entity.question.FavoriteQuestion;
import com.solver.db.entity.question.Question;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin("*")

@Api(value="질문 API", tags = {"Question"})
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {
	@Autowired
	QuestionService questionService;
	
	@Autowired
	BookmarkQuestionService bookmarkService;
	
	@Autowired
	FavoriteQuestionService favoriteQuestionService;
	
	@Autowired
	ConferenceReservationService conferenceReservationService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	// 전체 질문 조회 API - 메인 카테고리, 서브 카테고리, 검색어, 난이도, 질문 상태(해결, 미해결, ...), 정렬 기준(최신순, ...)에 따라 질문 목록 조회
	@GetMapping("/list")
	@ApiOperation(value = "질문 목록", notes = "질문 목록을 조회하는 API") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "질문 목록을 성공적으로 조회했습니다."),
        @ApiResponse(code = 400, message = "질문 목록 조회에 실패했습니다.")
    })
	public ResponseEntity<? extends BaseResponse> getQuestionList(
			@ModelAttribute QuestionGetListReq questionGetListReq)
	{	
		QuestionListRes questionListRes;
		try {
			questionListRes = questionService.getQuestionList(questionGetListReq);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(QuestionListRes.of(400, "질문 목록 조회에 실패했습니다."));
		}
		
		return ResponseEntity.status(200).body(questionListRes);
	}
	
	// 질문 생성 API
	@PostMapping()
	@ApiOperation(value = "질문 생성", notes = "질문을 생성하는 API") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "질문을 성공적으로 생성했습니다."),
        @ApiResponse(code = 400, message = "질문 생성에 실패했습니다.")
    })
	public ResponseEntity<? extends BaseResponse> createQuestion(
			HttpServletResponse response, 
			@RequestBody @ApiParam(value="질문 정보", required=true) QuestionPostReq questionPostReq,
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		// accessToken은 'Bearer token' 형태로 넘어오므로 처리가 필요하다.
		String token = accessToken.split(" ")[1];
		Question question = questionService.createQuestion(questionPostReq, token, response);
		if (question == null) {
			return ResponseEntity.status(400).body(QuestionRes.of(400, "질문 생성에 실패했습니다."));
		}
		
		QuestionCreateRes questionCreateRes = new QuestionCreateRes();
		questionCreateRes.setStatusCode(200);
		questionCreateRes.setQuestionId(question.getId());
		
		return ResponseEntity.status(201).body(questionCreateRes);
	}
	
	// 질문 상세조회 API
	@GetMapping("/{questionId}/info")
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
	
	// 질문 수정 API
	@PatchMapping("/{questionId}")
	@ApiOperation(value = "질문 수정", notes = "질문 수정 API") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "질문을 성공적으로 수정했습니다."),
        @ApiResponse(code = 403, message = "권한이 없습니다."),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
    })
	public ResponseEntity<? extends BaseResponse> updateQuestion(
			HttpServletResponse response, 
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
		question = questionService.updateQuestion(questionPatchReq, optionalQuestion.get(), token, response);
		
		if (question == null) {
			return ResponseEntity.status(403).body(QuestionRes.of(403, "권한이 없습니다."));
		}
		
		return ResponseEntity.status(201).body(QuestionRes.of(200, "질문을 성공적으로 수정했습니다."));
	}
	
	// 질문 삭제 API
	@DeleteMapping("/{questionId}")
	@ApiOperation(value = "질문 삭제", notes = "질문 삭제 API") 
    @ApiResponses({
        @ApiResponse(code = 204, message = "질문을 성공적으로 삭제했습니다."),
        @ApiResponse(code = 403, message = "권한이 없습니다."),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
    })
	public ResponseEntity<? extends BaseResponse> deleteQuestion(
			HttpServletResponse response, 
			@PathVariable @ApiParam(value="질문 Id", required=true) String questionId,
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		String token = accessToken.split(" ")[1];
		Optional<Question> question = questionService.getById(questionId);
		if (question == null) {
			return ResponseEntity.status(404).body(QuestionRes.of(404, "존재하지 않는 질문입니다."));
		}
		
		try {
			questionService.deleteQuestion(question.get(), token, response);
		} catch (AccessDeniedException e) {
			return ResponseEntity.status(403).body(QuestionRes.of(403, "권한이 없습니다."));
		}
		
		return ResponseEntity.status(204).body(QuestionRes.of(204, "질문을 성공적으로 삭제했습니다."));
	}
	
	// 내 질문 목록 API
	@GetMapping("/me")
	@ApiOperation(value = "내가 남긴 질문 목록", notes = "내 질문 목록 API")
	@ApiResponses({
		@ApiResponse(code = 200, message = "질문 목록을 성공적으로 조회했습니다."),
        @ApiResponse(code = 400, message = "질문 목록 조회에 실패했습니다.")
	})
	public ResponseEntity<? extends BaseResponse> myQuestion(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		String token = accessToken.split(" ")[1];
		List<Question> questionList;
		try {
			questionList = questionService.getMyQuestionList(token, response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(400).body(QuestionListRes.of(400, "질문 목록 조회에 실패했습니다."));
		}
		
		return ResponseEntity.status(200).body(QuestionMeRes.of(200, "질문 목록을 성공적으로 조회했습니다.", questionList));
		
	}
	
	/* 질문 북마크 추가 */	
	@PostMapping("/{questionId}/bookmark")
	@ApiOperation(value = "북마크 추가", notes = "질문 북마크 추가")
	@ApiResponses({
		@ApiResponse(code = 200, message = "북마크 추가 성공"),
        @ApiResponse(code = 400, message = "북마크 추가 실패"),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
	})
	public ResponseEntity<? extends BaseResponse> createBookmark(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String questionId
			)
	{
		Optional<Question> question = questionService.getById(questionId);
		
		if (question == null) {
			return ResponseEntity.status(404).body(BaseResponse.of(404, "존재하지 않는 질문입니다."));
		}
		
		boolean isSuccess = bookmarkService.createBookmark(accessToken, question.get(), response);
		
		if(!isSuccess) {
			return ResponseEntity.status(400).body(BaseResponse.of(400, "북마크 추가 실패"));
		}
		
		return ResponseEntity.status(200).body(BaseResponse.of(200, "북마크 추가 성공"));
	}
	
	// 북마크 취소
	@DeleteMapping("/{questionId}/bookmark")
	@ApiOperation(value = "북마크 취소", notes = "질문 북마크 취소")
	@ApiResponses({
		@ApiResponse(code = 204, message = "북마크 취소 성공"),
        @ApiResponse(code = 404, message = "북마크 기록이 없습니다."),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
	})
	public ResponseEntity<? extends BaseResponse> deleteBookmark(
			HttpServletResponse response, 
			@PathVariable @ApiParam(value="질문 Id", required=true) String questionId,
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		String token = accessToken.split(" ")[1];
		Optional<Question> question = questionService.getById(questionId);
		if (question == null) {
			return ResponseEntity.status(404).body(BaseResponse.of(404, "존재하지 않는 질문입니다."));
		}
		
		try {
			bookmarkService.deleteBookmark(question.get(), token, response);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body(BaseResponse.of(404, "북마크 기록이 없습니다."));
		}
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "북마크 취소 성공"));
	}
	
	// 질문 좋아요 
	@PostMapping("/{questionId}/recommend")
	@ApiOperation(value = "질문 좋아요", notes = "질문 좋아요")
	@ApiResponses({
		@ApiResponse(code = 201, message = "좋아요 성공"),
        @ApiResponse(code = 400, message = "좋아요 실패"),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
	})
	public ResponseEntity<? extends BaseResponse> createFavoriteQuestion(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String questionId
			)
	{
		Optional<Question> question = questionService.getById(questionId);
		
		if (question == null) {
			return ResponseEntity.status(404).body(BaseResponse.of(404, "존재하지 않는 질문입니다."));
		}
		
		FavoriteQuestion favoriteQuestion = favoriteQuestionService.createFavoriteQuestion(accessToken, question.get(), response);
		
		if(favoriteQuestion == null) {
			return ResponseEntity.status(400).body(BaseResponse.of(400, "좋아요 실패"));
		}
		
		return ResponseEntity.status(200).body(BaseResponse.of(200, "좋아요 성공"));
	}
	
	// 좋아요 취소
	@DeleteMapping("/{questionId}/recommend")
	@ApiOperation(value = "질문 좋아요 취소", notes = "질문 좋아요 취소")
	@ApiResponses({
		@ApiResponse(code = 204, message = "좋아요 취소 성공"),
        @ApiResponse(code = 404, message = "좋아요 기록이 없습니다."),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
	})
	public ResponseEntity<? extends BaseResponse> deleteFavoriteQuestion(
			HttpServletResponse response, 
			@PathVariable @ApiParam(value="질문 Id", required=true) String questionId,
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		String token = accessToken.split(" ")[1];
		Optional<Question> question = questionService.getById(questionId);
		if (question == null) {
			return ResponseEntity.status(404).body(BaseResponse.of(404, "존재하지 않는 질문입니다."));
		}
		
		try {
			favoriteQuestionService.deleteFavoriteQuestion(token, question.get(), response);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body(BaseResponse.of(404, "좋아요 기록이 없습니다."));
		}
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "좋아요 취소 성공"));
	}
	
	// 참관 신청
	@PostMapping("/{questionId}/observers")
	@ApiOperation(value = "참관 신청", notes = "참관 신청 API")
	@ApiResponses({
		@ApiResponse(code = 201, message = "참관 신청 성공"),
        @ApiResponse(code = 400, message = "참관 신청 실패"),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
	})
	public ResponseEntity<? extends BaseResponse> createConferenceReservation(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String questionId
			)
	{
		String token = accessToken.split(" ")[1];
		Optional<Question> question = questionService.getById(questionId);
		
		if (question == null) {
			return ResponseEntity.status(404).body(BaseResponse.of(404, "존재하지 않는 질문입니다."));
		}
		
		ConferenceReservation conferenceReservation = conferenceReservationService.createConferenceReservation(token, question.get(), response);
		
		if(conferenceReservation == null) {
			return ResponseEntity.status(400).body(BaseResponse.of(400, "참관 신청 실패"));
		}
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "참관 신청 성공"));
	}
	
	// 참관 신청 취소
	@DeleteMapping("/{questionId}/observers")
	@ApiOperation(value = "참관 신청 취소", notes = "참관 신청 취소 API")
	@ApiResponses({
		@ApiResponse(code = 204, message = "참관 신청 취소 성공"),
        @ApiResponse(code = 404, message = "참관 신청 기록이 없습니다."),
        @ApiResponse(code = 404, message = "존재하지 않는 질문입니다.")
	})
	public ResponseEntity<? extends BaseResponse> deleteConferenceReservation(
			HttpServletResponse response, 
			@PathVariable @ApiParam(value="질문 Id", required=true) String questionId,
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		String token = accessToken.split(" ")[1];
		Optional<Question> question = questionService.getById(questionId);
		if (question == null) {
			return ResponseEntity.status(404).body(BaseResponse.of(404, "존재하지 않는 질문입니다."));
		}
		
		try {
			conferenceReservationService.deleteConferenceReservation(token, question.get(), response);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body(BaseResponse.of(404, "참관 신청 기록이 없습니다."));
		}
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "참관 신청 취소 성공"));
	}
}
