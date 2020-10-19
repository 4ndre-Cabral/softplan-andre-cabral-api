package com.softplan.desafio.api.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.softplan.desafio.auth.payload.request.LoginRequest;
import com.softplan.desafio.exception.NotFoundException;
import com.softplan.desafio.service.AuthService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	@ApiOperation(value = "Login")
	@ApiResponses({
		@ApiResponse(code = 401, message = "Acesso não autorizado."), 
		@ApiResponse(code = 400, message = "Solicitação inválida.")
	})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> userAutentication(@Valid @RequestBody LoginRequest loginRequest) {
		
		return authService.signin(loginRequest);
	}
	
	@GetMapping("/refresh")
	@ApiOperation(value = "Refresh token")
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses({ @ApiResponse(code = 200, message = "Refresh token") })
	public ResponseEntity<String> refresh(ServletRequest request) throws NotFoundException {
		return authService.refresh((HttpServletRequest) request);
	}
}
