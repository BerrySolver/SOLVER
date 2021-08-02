package com.solver.common.auth;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solver.common.model.OAuthToken;

@Component
public class KakaoUtil {
	public OAuthToken getKakaoToken(String code) {
		//HttpHeader 오브젝트
    	RestTemplate tokenRt = new RestTemplate();
    	HttpHeaders tokenHeaders = new HttpHeaders();
    	tokenHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    	
    	//HttpBody 오브젝트
    	MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
    	tokenParams.add("grant_type", "authorization_code");
    	tokenParams.add("client_id", "4d0b843e88238ebf6614549fce8bff85");
    	tokenParams.add("redirect_uri", "http://localhost:8080/api/v1/auth/login");
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
    	
    	return oauthToken;
	}
	
	public long getKakaoUserId(OAuthToken oauthToken) throws ParseException {
    	//HttpHeader 오브젝트
    	RestTemplate rt = new RestTemplate();
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Bearer "+oauthToken.getAccess_token());
    	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    	
    	//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    	HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
    			new HttpEntity<>(headers);
    	
    	//Http 요청하기 - Post - response 변수의 웅답 받음
    	ResponseEntity<String> response = rt.exchange(
    			"https://kapi.kakao.com/v2/user/me",
    			HttpMethod.POST,
    			kakaoProfileRequest,
    			String.class
    			);
    	
    	JSONParser jsonParser = new JSONParser();
    	
    	JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
    	
    	Long kakaoId = (Long) jsonObject.get("id");
    	
    	return kakaoId;
	}

	public long getKakaoUserIdByToken(String accessToken) {
    	//HttpHeader 오브젝트
    	RestTemplate rt = new RestTemplate();
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Bearer "+accessToken);
    	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    	
    	//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    	HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
    			new HttpEntity<>(headers);
    	
    	//Http 요청하기 - Post - response 변수의 웅답 받음
    	ResponseEntity<String> response = rt.exchange(
    			"https://kapi.kakao.com/v2/user/me",
    			HttpMethod.POST,
    			kakaoProfileRequest,
    			String.class
    			);
    	
    	JSONParser jsonParser = new JSONParser();
    	
    	JSONObject jsonObject = null;
    	
		try {
			jsonObject = (JSONObject) jsonParser.parse(response.getBody());
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	Long kakaoId = (Long) jsonObject.get("id");
    	
    	return kakaoId;
		
	}
}
