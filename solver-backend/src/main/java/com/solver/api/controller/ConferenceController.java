package com.solver.api.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.ConferenceLogPostReq;
import com.solver.api.request.ConferenceRecordPostReq;
import com.solver.api.service.ConferenceLogService;
import com.solver.api.service.ConferenceService;
import com.solver.common.model.BaseResponse;

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
        @ApiResponse(code = 204, message = "화상 회의 종료 성공"),
        @ApiResponse(code = 409, message = "화상 회의 종료 실패")
    })
	public ResponseEntity<? extends BaseResponse> recordConference(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken,
			@PathVariable String questionId,
			@RequestBody ConferenceRecordPostReq conferenceRecordPostReq
			) throws IOException, ParseException 
	{
//		int flag = conferenceService.deleteConference(accessToken, conferenceId, response);
//		
//		if(flag != 3) {
//			return ResponseEntity.status(409).body(BaseResponse.of(409, "화상 회의 종료 실패"));
//		}
		
//		System.out.println(videoFile);
//		
//		for (byte b : videoFile) {
//			System.out.println(b);
//		}
//		
//		System.out.println(videoFile.length);
//		
//		int lByteArraySize = videoFile.length;
//
//
//
//	    try{
//
//	        File lOutFile = new File(uploadPath+File.separator+"test.webm");
//
//	        FileOutputStream lFileOutputStream = new FileOutputStream(lOutFile);
//
//	        lFileOutputStream.write(videoFile);
//
//	        lFileOutputStream.close();
//
//	    }catch(Throwable e){
//
//	       System.out.println("!@!@@!@!@");
//
//	    }
		
//		String str = new ClassPathResource("/static/").getPath();
		
		UUID uuid = UUID.randomUUID();
		
		String videoFile = conferenceRecordPostReq.getVideoFile();
		
		String saveFilename = uuid+".mp4";
		
    	
        Decoder decoder = Base64.getDecoder();
        byte[] decodedByte = decoder.decode(videoFile);
        FileOutputStream fos = new FileOutputStream(uploadPath+File.separator+saveFilename);
        fos.write(decodedByte);
        fos.close();
		
		return ResponseEntity.status(204).body(BaseResponse.of(204, "화상 회의 종료 성공"));
	}
}
