package com.solver.common.auth;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solver.common.model.OAuthToken;
import com.solver.common.model.TokenResponse;
import com.solver.db.entity.user.Token;
import com.solver.db.repository.user.TokenRepository;

@Component
public class KakaoUtil {
	
	@Autowired
	TokenRepository tokenRepository;
	
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

	public TokenResponse getKakaoUserIdByToken(String accessToken) {
		TokenResponse tokenResponseData = new TokenResponse();
		
		
    	//HttpHeader 오브젝트
    	RestTemplate rt = new RestTemplate();
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Bearer "+accessToken);
    	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    	
    	//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
    	HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
    			new HttpEntity<>(headers);
    	
    	ResponseEntity<String> response = null;
    	
    	try {
    		//Http 요청하기 - Post - response 변수의 웅답 받음
    		response = rt.exchange(
    				"https://kapi.kakao.com/v2/user/me",
    				HttpMethod.POST,
    				kakaoProfileRequest,
    				String.class
    				);
    	}
    	//만료된 엑세스 토큰이면 갱신
    	catch(HttpClientErrorException e) {
    		Token token = tokenRepository.findByAccessToken(accessToken);
    		
    		//토큰이 일치하지 않는 경우
    		if(!token.getAccessToken().equals(accessToken)) {
    			
    		}
    		
        	RestTemplate tokenRt = new RestTemplate();
        	HttpHeaders tokenHeaders = new HttpHeaders();
        	tokenHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        	
        	//HttpBody 오브젝트
        	MultiValueMap<String, String> tokenParams = new LinkedMultiValueMap<>();
        	tokenParams.add("grant_type", "refresh_token");
        	tokenParams.add("client_id", "4d0b843e88238ebf6614549fce8bff85");
        	tokenParams.add("refresh_token", token.getRefreshToken());
    		
        	HttpEntity<MultiValueMap<String, String>> tokenRefreshRequest =
        			new HttpEntity<>(tokenParams, tokenHeaders);
        	
        	ResponseEntity<String> tokenResponse = tokenRt.exchange(
    				"https://kauth.kakao.com/oauth/token",
    				HttpMethod.POST,
    				tokenRefreshRequest,
    				String.class
    				);
        	
        	JSONParser jsonParser = new JSONParser();
        	
        	JSONObject jsonObject = null;
        	
    		try {
    			jsonObject = (JSONObject) jsonParser.parse(tokenResponse.getBody());
    		} catch (ParseException e2) {
    			e2.printStackTrace();
    		}
        	
    		//갱신된 액세스 토큰 저장
        	token.setAccessToken((String)jsonObject.get("access_token"));
        	
        	String refreshToken = (String)jsonObject.get("refresh_token");
        	
        	//리프레시 토큰도 갱신된 경우
        	if(refreshToken != null) {
        		token.setRefreshToken(refreshToken);
        	}
        	
        	String refreshedAccessToken = (String) jsonObject.get("access_token");
        	
        	System.out.println(refreshedAccessToken);
        	tokenResponseData.setAccessToken(refreshedAccessToken);
        	
        	rt = new RestTemplate();
        	headers = new HttpHeaders();
        	
        	headers.add("Authorization", "Bearer "+refreshedAccessToken);
        	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        	
        	//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        	kakaoProfileRequest = new HttpEntity<>(headers);
        	
    		//Http 요청하기 - Post - response 변수의 웅답 받음
    		response = rt.exchange(
    				"https://kapi.kakao.com/v2/user/me",
    				HttpMethod.POST,
    				kakaoProfileRequest,
    				String.class
    				);
    		
        	jsonParser = new JSONParser();
        	
        	jsonObject = null;
        	
    		try {
    			jsonObject = (JSONObject) jsonParser.parse(response.getBody());
    		} catch (ParseException e2) {
    			e2.printStackTrace();
    		}
    		
        	
        	tokenRepository.save(token);
    	}
    	
    	
    	JSONParser jsonParser = new JSONParser();
    	
    	JSONObject jsonObject = null;
    	
		try {
			jsonObject = (JSONObject) jsonParser.parse(response.getBody());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		    	
    	Long kakaoId = (Long) jsonObject.get("id");
    	
    	tokenResponseData.setKakaoId(kakaoId);
    	
    	return tokenResponseData;
		
	}
}
