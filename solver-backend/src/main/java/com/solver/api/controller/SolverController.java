package com.solver.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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

import com.solver.api.request.QuestionPatchReq;
import com.solver.api.request.QuestionPostReq;
import com.solver.api.response.QuestionRes;
import com.solver.api.service.QuestionService;
import com.solver.api.service.UserService;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.BaseResponse;
import com.solver.db.entity.question.Question;
import com.solver.db.entity.user.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "답변자 API", tags = { "Solver" })
@RestController
@RequestMapping("/api/v1/solver")
public class SolverController {
	@Autowired
	UserService userService;

	@PostMapping()
	@ApiOperation(value = "답변자 목록", notes = "원하는 답변자를 찾아오기")
	@ApiResponses({ 
			@ApiResponse(code = 201, message = "정해진 조건에 알맞는 사람들을 찾아왔습니다."),
			@ApiResponse(code = 400, message = "잘못된 접근입니다. 다시 확인해주세요.") })
	public ResponseEntity<? extends BaseResponse> createQuestion(
			@ModelAttribute QuestionPostReq questionPostReq) 
	{
		
		return ResponseEntity.status(201).body(QuestionRes.of(201, "질문을 성공적으로 생성했습니다."));
	}
}
