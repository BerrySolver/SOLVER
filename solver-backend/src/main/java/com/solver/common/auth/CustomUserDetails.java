package com.solver.common.auth;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.solver.db.entity.Auth;
import com.solver.db.entity.User;

public class CustomUserDetails implements UserDetails{
	@Autowired
	User user;
	@Autowired
	Auth auth;
	
	
	public User getUser() {
		return user;
	}
	
	public CustomUserDetails(User user, Auth auth) {
		this.user = user;
		this.auth = auth;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.auth.getPassword();
	}

	@Override
	public String getUsername() {
		return this.auth.getLoginId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
