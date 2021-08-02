package com.solver.api.controller;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solver.api.request.UserLoginPostReq;
import com.solver.api.response.UserLoginRes;
import com.solver.api.service.UserService;
import com.solver.common.model.OAuthToken;
import com.solver.db.entity.user.User;
import com.solver.db.repository.user.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
	UserService userService;
	
	// 로그인. id와 password를 loginDto로 받음
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "아이디, 패스워드를 입력해 로그인") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "로그인에 성공했습니다"),
        @ApiResponse(code = 409, message = "로그인에 실패했습니다")
    })
	public ResponseEntity<UserLoginRes> loginUser(String code) throws ParseException {

    	//HttpHeader 오브젝트
    	RestTemplate tokenRt = new RestTemplate();
    	HttpHeaders tokenHeaders = new HttpHeaders();
    	tokenHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    	
    	//HttpBody 오브젝트
    	MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
    	tokenParams.add("grant_type", "authorization_code");
    	tokenParams.add("client_id", "4d0b843e88238ebf6614549fce8bff85");
    	tokenParams.add("redirect_uri", "http://localhost:8080/api/v1/test/login");
    	tokenParams.add("code", code);
    	
    	//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    	HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
    			new HttpEntity<>(tokenParams, tokenHeaders);
    	
    	//Http 요청하기 - Post - response 변수의 웅답 받음
    	ResponseEntity<String> tokenResponse = tokenRt.exchange(
    			"https://kauth.kakao.com/oauth/token",
    			HttpMethod.POST,
    			kakaoTokenRequest,
    			String.class
    	);
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	OAuthToken oauthToken = null;
    	
    	try {
			oauthToken = objectMapper.readValue(tokenResponse.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
    	System.out.println("카카오 엑세스 토큰 만료시간: "+oauthToken.getExpires_in());
    	System.out.println("카카오 리프레시 토큰 : "+oauthToken.getRefresh_token());
    	System.out.println("카카오 리프레시 토큰 만료시간: "+oauthToken.getRefresh_token_expires_in());
    	
    	//HttpHeader 오브젝트
    	RestTemplate rt2 = new RestTemplate();
    	HttpHeaders headers2 = new HttpHeaders();
    	headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
    	headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    	
    	//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    	HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
    			new HttpEntity<>(headers2);
    	
    	//Http 요청하기 - Post - response 변수의 웅답 받음
    	ResponseEntity<String> response2 = rt2.exchange(
    			"https://kapi.kakao.com/v2/user/me",
    			HttpMethod.POST,
    			kakaoProfileRequest,
    			String.class
    			);
    	
    	JSONParser jsonParser = new JSONParser();
    	
    	JSONObject jsonObject = (JSONObject) jsonParser.parse(response2.getBody());
    	
    	Long id = (Long) jsonObject.get("id");
    	
    	System.out.println(id);
    	
    	/*-------------------------------------------------*/
    	
//		Optional<User> user = userService.checkLoginId(userLoginPostReq.getLoginId());
//		
//		if(user.orElse(null) == null)
//			return ResponseEntity.status(409).body(null);
//		
//		
		String accessToken = "1111";
		
		UserLoginRes userLoginRes = UserLoginRes.builder().accessToken(accessToken).build();
		userLoginRes.setMessage("로그인에 성공했습니다");
		userLoginRes.setStatusCode(200);

		//jwt 토큰을 response body에 넣는 경우
		return ResponseEntity.status(200).body(userLoginRes);
	}
	
//	// 로그인. id와 password를 loginDto로 받음
//	@PostMapping("/login")
//	@ApiOperation(value = "로그인", notes = "아이디, 패스워드를 입력해 로그인") 
//	@ApiResponses({
//		@ApiResponse(code = 200, message = "로그인에 성공했습니다"),
//		@ApiResponse(code = 409, message = "로그인에 실패했습니다")
//	})
//	public ResponseEntity<UserLoginRes> loginUser( 
//			@ApiParam(value="회원가입 정보", required=true) @RequestBody UserLoginPostReq userLoginPostReq) {
//		
//		Optional<User> user = userService.checkLoginId(userLoginPostReq.getLoginId());
//		
//		if(user.orElse(null) == null)
//			return ResponseEntity.status(409).body(null);
//		
//		
//		String accessToken = "1111";
//		
//		UserLoginRes userLoginRes = UserLoginRes.builder().accessToken(accessToken).build();
//		userLoginRes.setMessage("로그인에 성공했습니다");
//		userLoginRes.setStatusCode(200);
//		
//		//jwt 토큰을 response body에 넣는 경우
//		return ResponseEntity.status(200).body(userLoginRes);
//	}
}
