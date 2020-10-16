package com.softplan.desafio.auth.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softplan.desafio.auth.payload.request.LoginRequest;
import com.softplan.desafio.auth.payload.request.SignupRequest;
import com.softplan.desafio.auth.service.AuthService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> userAutentication(@Valid @RequestBody LoginRequest loginRequest) {
		
		return authService.signin(loginRequest);
	}

	@PostMapping("/signup")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> userRegister(@Valid @RequestBody SignupRequest signUpRequest) {
		
		return authService.register(signUpRequest);
		
	}
}
