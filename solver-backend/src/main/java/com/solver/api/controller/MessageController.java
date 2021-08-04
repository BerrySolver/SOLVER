package com.solver.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.response.MessageListRes;
import com.solver.api.service.MessageService;
import com.solver.common.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true", allowedHeaders="*",
methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS})

@Api(value="메시지 API", tags = {"Message"})
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
	
	@Autowired
	MessageService messageService;
	
	@GetMapping("/forwardings")
	@ApiOperation(value = "보낸 메시지 목록", notes = "보낸 메시지 목록 조회") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "보낸 메시지 목록 조회 성공"),
        @ApiResponse(code = 409, message = "보낸 메시지 목록 실패")
    })
	public ResponseEntity<? extends BaseResponse> getSendMessageList(
			@ApiIgnore @RequestHeader("Authorization") String accessToken
			)
	{
		MessageListRes messageListRes = messageService.getSendMessageList(accessToken);
		
		return ResponseEntity.status(messageListRes.getStatusCode()).body(messageListRes);
	}
	
	@GetMapping("/receptions")
	@ApiOperation(value = "받은 메시지 목록", notes = "받은 메시지 목록 조회") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "받은 메시지 목록 조회 성공"),
        @ApiResponse(code = 409, message = "받은 메시지 목록 실패")
    })
	public ResponseEntity<? extends BaseResponse> getRecieveMessageList(
			@ApiIgnore @RequestHeader("Authorization") String accessToken
			)
	{
		MessageListRes messageListRes = messageService.getReceiveMessageList(accessToken);
		
		return ResponseEntity.status(messageListRes.getStatusCode()).body(messageListRes);
	}
}
