package com.solver.api.controller;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solver.common.model.OAuthToken;

@RestController
@RequestMapping("/api/v1/test")
public class KakaoController {

	@GetMapping(value="/login")
    public String kakaoConnect(String code) throws ParseException {
    	//HttpHeader 오브젝트
    	RestTemplate rt = new RestTemplate();
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    	
    	//HttpBody 오브젝트
    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    	params.add("grant_type", "authorization_code");
    	params.add("client_id", "4d0b843e88238ebf6614549fce8bff85");
    	params.add("redirect_uri", "http://localhost:8080/api/v1/test/login");
    	params.add("code", code);
    	
    	//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    	HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
    			new HttpEntity<>(params, headers);
    	
    	//Http 요청하기 - Post - response 변수의 웅답 받음
    	ResponseEntity<String> response = rt.exchange(
    			"https://kauth.kakao.com/oauth/token",
    			HttpMethod.POST,
    			kakaoTokenRequest,
    			String.class
    	);
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	OAuthToken oauthToken = null;
    	
    	try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
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
    	
//		/* 로그아웃 */
//    	//HttpHeader 오브젝트
//    	RestTemplate rt3 = new RestTemplate();
//    	HttpHeaders headers3 = new HttpHeaders();
//    	headers3.add("Authorization", "Bearer "+oauthToken.getAccess_token());
//    	headers3.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//    	
//    	//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
//    	HttpEntity<MultiValueMap<String, String>> kakaokRequest =
//    			new HttpEntity<>(headers2);
//    	
//    	//Http 요청하기 - Post - response 변수의 웅답 받음
//    	ResponseEntity<String> response2 = rt2.exchange(
//    			"https://kapi.kakao.com/v2/user/me",
//    			HttpMethod.POST,
//    			kakaoProfileRequest,
//    			String.class
//    			);
    	
    	
    	return response2.getBody();
    }
	
	@GetMapping(value="/logout")
    public String kakaoLogout(String code) {
		return "logout";
    }
}
