package com.solver.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.solver.api.response.UserLoginRes;
import com.solver.api.service.UserService;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.BaseResponse;
import com.solver.common.model.OAuthToken;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.code.CommonCode;
import com.solver.db.entity.user.Token;
import com.solver.db.entity.user.User;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.user.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin("*")

@Api(value="인증 API", tags = {"Auth"})
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CodeRepository codeRepository;
	
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
	public ModelAndView loginUser(String code) throws ParseException {
		//카카오 접근 토큰 받아오기
    	OAuthToken oauthToken = kakaoUtil.getKakaoToken(code);
    	
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
		
		UserLoginRes userLoginRes = new UserLoginRes();
		userLoginRes.setAccessToken(oauthToken.getAccess_token());
		userLoginRes.setMessage("로그인에 성공했습니다");
		userLoginRes.setStatusCode(200);
		
		String profileUrl = kakaoUtil.getKakaoProfileUrl(oauthToken.getAccess_token());
		
		userService.insertDefaultProfile(kakaoId ,profileUrl);
		
		ModelAndView mav = new ModelAndView("jsonView");
		
		//mav.setViewName("redirect:https://localhost:8081/auth/login");
		mav.setViewName("redirect:https://i5a507.p.ssafy.io/auth/login");
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
	public ResponseEntity<BaseResponse> logoutUser(
			HttpServletResponse response, 
			@ApiIgnore @RequestHeader("Authorization") String accessToken
			)
	{
		userService.deleteToken(accessToken, response);
		
		return ResponseEntity.status(200).body(BaseResponse.of(200, "로그아웃"));
	}
	
	@GetMapping("/category")
	@ApiOperation(value = "메인 카테고리 리스트", notes = "아이디, 패스워드를 입력해 로그인") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "로그아웃에 성공했습니다")
    })
	public List<Code> mainCategoryList(){
		
		CommonCode commonCode = new CommonCode();
		commonCode.setCommonCode("009");
		
		List<Code> categoryList = codeRepository.findByCommonCode(commonCode);
		
		return categoryList;
	}
}
