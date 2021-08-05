
package com.solver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.solver.common.auth.TokenFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/static/**")
		.antMatchers("/api/v1/answers/list/**")
		.antMatchers("/api/v1/profiles/*/info")
		.antMatchers("/api/v1/profiles/*/tab")
		.antMatchers("/api/v1/questions/list")
		.antMatchers("/api/v1/questions/*/info")
		.antMatchers("/api/v1/comments/list/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 하지않음
				.and().addFilter(new TokenFilter(authenticationManager()))
				.authorizeRequests()
				.antMatchers("/api/v1/notifications/**").authenticated()
				.antMatchers("/api/v1/answers/**").authenticated()
				.antMatchers("/api/v1/comments/**").authenticated()
				.antMatchers("/api/v1/conferences/**").authenticated()
				.antMatchers("/api/v1/messages/**").authenticated()
				.antMatchers("/api/v1/notifications/**").authenticated()
				.antMatchers("/api/v1/profiles/**").authenticated()
				.antMatchers("/api/v1/questions/**").authenticated()
				.antMatchers("/api/v1/reports/**").authenticated()
				.antMatchers("/api/v1/user").authenticated()
				.anyRequest()
				.permitAll()
				.and()
				.cors();

	}

}
