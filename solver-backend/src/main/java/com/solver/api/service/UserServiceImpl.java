package com.solver.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.OAuthToken;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.user.Token;
import com.solver.db.entity.user.TokenId;
import com.solver.db.entity.user.User;
import com.solver.db.repository.user.TokenRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenRepository tokenRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	@Override
	public Optional<User> checkNickname(String nickname) {
		Optional<User> user = userRepository.findByNickname(nickname);
		
		return user;
	}


	@Override
	public Optional<User> getUserByKakaoId(Long kakaoId) {
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
		return user;
	}


	@Override
	public User insertUser(Long kakaoId) {
		User user = new User();
		
		user.setKakaoId(kakaoId);
		
		String userId = "";
		
		//새로운 user테이블의 id생성
		while(true) {
			userId = RandomIdUtil.makeRandomId(13);
			
			if(userRepository.findById(userId).orElse(null) == null)
				break;
		}
		
		user.setId(userId);
		
		return userRepository.save(user);
	}

	@Override
	public Token insertToken(OAuthToken oauthToken, Long kakaoId) {
		Optional<User> user = userRepository.findByKakaoId(kakaoId);
		
		TokenId tokenId = new TokenId();
		tokenId.setTokenId(user.get().getId());
		
		//토큰 정보 저장
		Token token = new Token();
		token.setAccessToken(oauthToken.getAccess_token());
		token.setRefreshToken(oauthToken.getRefresh_token());
		token.setUser(user.get());
		token.setTokenId(tokenId);
		
		//DB에 저장
		return tokenRepository.save(token);
	}


	@Override
	public void deleteToken(String accessToken) {
		long id = kakaoUtil.getKakaoUserIdByToken(accessToken);
		
		Optional<User> user = userRepository.findByKakaoId(id);
		
		//userId로 token테이블에서 데이터 삭제
		tokenRepository.deleteByUserId(user.get().getId());
	}
}
