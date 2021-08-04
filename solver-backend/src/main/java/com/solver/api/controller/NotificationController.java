package com.solver.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.response.NotificationListRes;
import com.solver.api.service.NotificationService;
import com.solver.common.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true", allowedHeaders="*",
methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS})

@Api(value="알림 API", tags = {"Notification"})
@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	@GetMapping()
	@ApiOperation(value = "전체 알림 목록", notes = "전체 알림 목록 조회") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "전체 알림 목록 조회 성공"),
        @ApiResponse(code = 409, message = "전체 알림 목록 조회 실패")
    })
	public ResponseEntity<? extends BaseResponse> getAllNotificationList(
			@ApiIgnore @RequestHeader("Authorization") String accessToken
			)
	{
		NotificationListRes notificationListRes = notificationService.getAllNotificationList(accessToken);
		
		return ResponseEntity.status(notificationListRes.getStatusCode()).body(notificationListRes);
	}
}
