package com.solver.common.util;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.solver.common.auth.TokenProvider;
import com.solver.db.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil implements InitializingBean{

	private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private static final String AUTHORITIES_KEY = "authorization";
	private static final String PREFIX = "Bearer ";

	private final String secret;
	private final long accessTokenValidity;
	private final long refreshTokenValidity;

	private Key key;

	public JwtTokenUtil(@Value("${jwt.secret}") String secret,
			@Value("${jwt.token-validity-in-seconds}") long accessTokenValidity,
			@Value("${jwt.token-validity-in-seconds}") long refreshTokenValidity) {
		this.secret = secret;
		this.accessTokenValidity = accessTokenValidity * 1000;
		this.refreshTokenValidity = refreshTokenValidity * 10000;
	}

	// 빈이 생성이 되고 의존성이 주입된 후에 secret 값을 디코드 해서 키에 할당하기 위해서 사용
	@Override
	public void afterPropertiesSet() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	
	public String createAccessToken(User user) {
		// 토큰의 만료시간 설정
		long now = (new Date()).getTime();
		Date validity = new Date(now + this.accessTokenValidity);

		// 토큰을 생성해서 리턴
		return Jwts.builder().setSubject(user.getId()).claim("loginId", user.getLoginId()).claim("nickname", user.getNickname()).signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity).compact();
	}
	
	public String createRefreshToken(User user) {
		// 토큰의 만료시간 설정
		long now = (new Date()).getTime();
		Date validity = new Date(now + this.refreshTokenValidity);
		
		// 토큰을 생성해서 리턴
		return Jwts.builder().setSubject(user.getId()).claim("loginId", user.getLoginId()).signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity).compact();
	}

	// 토큰의 유효성 검사
	public boolean validateToken(String token) {
		try {
			// 토큰 파싱
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			logger.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			logger.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			logger.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			logger.info("JWT 토큰이 잘못되었습니다.");
		}
		// 문제가 있는 경우
		return false;
	}
	
	public String getNicknameFromToken(String token) {
		Jws<Claims> jwtClaims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		String nickname = (String) jwtClaims.getBody().get("nickname");
		
		return nickname;
	}
	
	public String getLoginIdFromToken(String token) {
		Jws<Claims> jwtClaims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		String loginId = (String) jwtClaims.getBody().get("loginId");
		
		return loginId;
	}
}
