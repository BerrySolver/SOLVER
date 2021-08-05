package com.solver.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.solver.api.response.UserLoginRes;
import com.solver.api.service.UserService;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.BaseResponse;
import com.solver.common.model.OAuthToken;
import com.solver.db.entity.user.Token;
import com.solver.db.entity.user.User;
import com.solver.db.repository.user.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "http://localhost:8081", allowCredentials="true", allowedHeaders="*",
methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.HEAD, RequestMethod.OPTIONS})

@Api(value="인증 API", tags = {"Auth"})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	@Autowired
	UserService userService;
	
	// 로그인. id와 password를 loginDto로 받음
	@GetMapping(value="/login")
	@ApiOperation(value = "로그인", notes = "아이디, 패스워드를 입력해 로그인") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "로그인에 성공했습니다"),
        @ApiResponse(code = 409, message = "로그인에 실패했습니다")
    })
//	public ResponseEntity<UserLoginRes> loginUser(String code) throws ParseException {
	public ModelAndView loginUser(String code) throws ParseException {
		//카카오 접근 토큰 받아오기
    	OAuthToken oauthToken = kakaoUtil.getKakaoToken(code);
    	
    	System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
    	System.out.println("카카오 엑세스 토큰 만료시간: "+oauthToken.getExpires_in());
    	System.out.println("카카오 리프레시 토큰 : "+oauthToken.getRefresh_token());
    	System.out.println("카카오 리프레시 토큰 만료시간: "+oauthToken.getRefresh_token_expires_in());
    	
    	//카카오 토큰으로 유저 아이디 받아오기
    	Long kakaoId = kakaoUtil.getKakaoUserId(oauthToken);
    	
    	//DB에 해당 카카오ID로 저장된 데이터 가져오기
		Optional<User> user = userService.getUserByKakaoId(kakaoId);
		
		//DB에 없는 아이디인 경우 -> DB에 저장 해야 함
		if(user.orElse(null) == null) {
			//DB에 해당 카카오ID로 빈 데이터 저장
			User createdUser = userService.insertUser(kakaoId);
		}
		
		Token createdToken = userService.insertToken(oauthToken, kakaoId);
		
		UserLoginRes userLoginRes = UserLoginRes.builder().accessToken(oauthToken.getAccess_token()).build();
		userLoginRes.setMessage("로그인에 성공했습니다");
		userLoginRes.setStatusCode(200);
		
		//access 토큰을 body에 넣어서 프론트 전송
//		return ResponseEntity.status(200).body(userLoginRes);
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		mav.setViewName("redirect:http://localhost:8081/auth/login");
		mav.addObject("accessToken", oauthToken.getAccess_token());

		RedirectAttributes ra = new RedirectAttributesModelMap();
		ra.addFlashAttribute("token", oauthToken.getAccess_token());
	    return mav;
	}
	
	@GetMapping("/logout")
	@ApiOperation(value = "로그아웃", notes = "아이디, 패스워드를 입력해 로그인") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "로그아웃에 성공했습니다")
    })
	public ResponseEntity<BaseResponse> logoutUser(@RequestParam(value = "state") String accessToken){
		userService.deleteToken(accessToken);
		
		return ResponseEntity.status(200).body(BaseResponse.of(200, "로그아웃"));
	}
}
