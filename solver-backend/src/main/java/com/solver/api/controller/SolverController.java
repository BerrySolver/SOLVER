package com.solver.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.request.SolverGetListReq;
import com.solver.api.response.SolverListRes;
import com.solver.api.response.SolverRes;
import com.solver.api.service.UserService;
import com.solver.common.model.BaseResponse;
import com.solver.db.repository.code.CategoryRepository;
import com.solver.db.repository.code.CodeRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin("*")
@Api(value = "답변자 API", tags = { "Solver" })
@RestController
@RequestMapping("/api/v1/solver")
public class SolverController {
	@Autowired
	UserService userService;
	
	@Autowired
	CodeRepository codeRepository;
	
	@Autowired
	CategoryRepository categoryRepository;


	@GetMapping()
	@ApiOperation(value = "답변자 목록", notes = "원하는 답변자를 찾아오기")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "정해진 조건에 알맞는 사람들을 찾아왔습니다."),
			@ApiResponse(code = 400, message = "잘못된 접근입니다. 다시 확인해주세요.") })
	public ResponseEntity<? extends BaseResponse> getSolverList(
			@ModelAttribute SolverGetListReq solverGetListReq) 
	{		
		List<SolverRes> list;
		
		try {
			list = userService.getUserList(solverGetListReq);
		} catch (Exception e) {
			return ResponseEntity.status(400).body(SolverListRes.of(400, "잘못된 접근입니다."));
		}
		
		return ResponseEntity.status(200).body(SolverListRes.of(200, "정해진 조건에 알맞는 사람들을 찾아왔습니다.", list));
	}
}
