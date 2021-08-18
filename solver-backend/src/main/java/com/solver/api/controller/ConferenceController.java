package com.solver.api.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.ConferenceLogPostReq;
import com.solver.api.request.ConferenceRecordPostReq;
import com.solver.api.request.EvaluationPostReq;
import com.solver.api.response.ConferenceReservationRes;
import com.solver.api.service.ConferenceLogService;
import com.solver.api.service.ConferenceReservationService;
import com.solver.api.service.ConferenceService;
import com.solver.common.model.BaseResponse;
import com.solver.db.entity.conference.ConferenceReservation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin("*")
@Api(value="화상회의 API", tags = {"Conference"})
@RestController
@RequestMapping("/api/v1/conferences")
public class ConferenceController {

	@Autowired
	ConferenceService conferenceService;
	
	@Autowired
	ConferenceReservationService conferenceReservationService;
	
	@Autowired
	ConferenceLogService conferenceLogService;
	
	String uploadPath = "C:" + File.separator + "Users" + File.separator + "HM" 
			+ File.separator + "downloads";
	
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
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String conferenceId
			) 
	{
		int flag = conferenceService.updateConference(accessToken, conferenceId, response);
		
		if(flag != 3) {
			return ResponseEntity.status(409).body(BaseResponse.of(409, "상태 변경 실패"));
		}
		
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "상태 변경 성공"));
	}
	
	/* 질문 화상 회의 상태 변경 
	 * 
	 * 상태 변경은 public으로 변환만 하는 건가?
	 * */
	@PostMapping(value="/{conferenceId}")
	@ApiOperation(value = "화상 회의 상태 변경", notes = "화상 회의 공개여부 상태 변경") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "상태 변경 성공"),
        @ApiResponse(code = 409, message = "상태 변경 실패")
    })
	public ResponseEntity<? extends BaseResponse> createConferenceLog(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String conferenceId,
			@RequestBody ConferenceLogPostReq conferenceLogPostReq
			) 
	{
		int flag = conferenceLogService.createConferenceLog(accessToken, conferenceId, response, conferenceLogPostReq);
		
		if(flag != 3) {
			return ResponseEntity.status(409).body(BaseResponse.of(409, "상태 변경 실패"));
		}
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "상태 변경 성공"));
	}
	
	/* 화상 회의 나가기 */
	@DeleteMapping(value="/{conferenceId}/{nickname}")
	@ApiOperation(value = "화상 회의 나가기", notes = "참관자 혹은 질문자의 화상 회의 나가기") 
    @ApiResponses({
        @ApiResponse(code = 204, message = "화상 회의 나가기 성공"),
        @ApiResponse(code = 409, message = "화상 회의 나가기 실패")
    })
	public ResponseEntity<? extends BaseResponse> goOutConference(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String conferenceId
			) 
	{
		int flag = conferenceService.goOutConference(accessToken, conferenceId, response);
		
		if(flag != 3) {
			return ResponseEntity.status(409).body(BaseResponse.of(409, "화상 회의 나가기 실패"));
		}
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "화상 회의 나가기 성공"));
	}
	
	/* 화상 회의 종료 */
	@DeleteMapping(value="/{conferenceId}")
	@ApiOperation(value = "화상 회의 종료", notes = "화상 회의를 닫는다") 
    @ApiResponses({
        @ApiResponse(code = 204, message = "화상 회의 종료 성공"),
        @ApiResponse(code = 409, message = "화상 회의 종료 실패")
    })
	public ResponseEntity<? extends BaseResponse> deleteConference(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String conferenceId
			) 
	{
		int flag = conferenceService.deleteConference(accessToken, conferenceId, response);
		
		if(flag != 3) {
			return ResponseEntity.status(409).body(BaseResponse.of(409, "화상 회의 종료 실패"));
		}
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "화상 회의 종료 성공"));
	}
	
	/* 화상회의 영상 저장 */
	@PostMapping(value="/record/{questionId}")
	@ApiOperation(value = "화상 회의 종료", notes = "화상 회의를 닫는다") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "영상 저장 완료"),
        @ApiResponse(code = 409, message = "화상 회의 종료 실패")
    })
	public ResponseEntity<? extends BaseResponse> recordConference(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String questionId,
			@RequestBody ConferenceRecordPostReq conferenceRecordPostReq
			) throws IOException, ParseException 
	{
		
		System.out.println(accessToken);
		conferenceService.recordConference(accessToken, questionId, response, conferenceRecordPostReq);
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "화상 회의 종료 성공"));
	}
	
	/* 화상회의 영상 저장 */
	@PostMapping(value="/evaluation")
	@ApiOperation(value = "화상 회의 종료", notes = "화상 회의를 닫는다") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "영상 저장 완료"),
        @ApiResponse(code = 409, message = "화상 회의 종료 실패")
    })
	public ResponseEntity<? extends BaseResponse> insertEvaluation(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@RequestBody EvaluationPostReq evaluationPostReq
			) throws IOException, ParseException 
	{

		conferenceService.insertEvaluation(accessToken, evaluationPostReq, response);
		
		return ResponseEntity.status(201).body(BaseResponse.of(201, "화상 회의 종료 성공"));
	}
	
	/* 화상회의 영상 저장 */
	@GetMapping(value="/reservations")
	@ApiOperation(value = "화상 회의 종료", notes = "화상 회의를 닫는다") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "예약 목록 조회 성공"),
        @ApiResponse(code = 409, message = "예약 목록 조회 실패")
    })
	public ResponseEntity<? extends BaseResponse> getReservationList(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken
			) throws IOException, ParseException 
	{

		List<ConferenceReservation> list = conferenceReservationService.getReservationList(accessToken, response);
		
		
		if(list == null) {
			return ResponseEntity.status(409).body(BaseResponse.of(409, "예약 목록 조회 실패"));
		}
		
		return ResponseEntity.status(201).body(ConferenceReservationRes.of(list, 201, "예약 목록 조회 성공"));
	}
}
