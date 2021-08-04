package com.solver.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.ReportPostReq;
import com.solver.api.response.QuestionRes;
import com.solver.api.service.ReportService;
import com.solver.common.model.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true", allowedHeaders="*",
methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS})

@Api(value="신고 API", tags = {"Report"})
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	// 신고 생성 API
	@PostMapping()
	@ApiOperation(value = "신고 생성", notes = "신고 API") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "신고가 접수되었습니다."),
        @ApiResponse(code = 400, message = "신고 접수에 실패했습니다.")
    })
	public ResponseEntity<? extends BaseResponse> createReport(
			@RequestBody @ApiParam(value="신고 정보", required=true) ReportPostReq reportPostReq,
			@ApiIgnore @RequestHeader("Authorization") String accessToken)
	{
		String token = accessToken.split(" ")[1];
		int flag = 1;
		
		if (reportPostReq.getTarget().equals("question")) {
			if (reportService.createReportQuestion(reportPostReq, token) == null) {
				flag = 0;
			}
		} else if (reportPostReq.getTarget().equals("answer")) {
			if (reportService.createReportAnswer(reportPostReq, token) == null) {
				flag = 0;
			}
		} else if (reportPostReq.getTarget().equals("comment")) {
			if (reportService.createReportComment(reportPostReq, token) == null) {
				flag = 0;
			}
		}
		
		if (flag == 0) {
			return ResponseEntity.status(400).body(BaseResponse.of(400, "신고 접수에 실패했습니다."));
		}		
		
		return ResponseEntity.status(201).body(QuestionRes.of(201, "신고가 접수되었습니다."));
	}
	
}
