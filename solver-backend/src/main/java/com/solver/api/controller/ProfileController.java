package com.solver.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solver.api.service.UserService;

import io.swagger.annotations.Api;

@Api(value="유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
	@Autowired
	UserService	userService;
	
}
