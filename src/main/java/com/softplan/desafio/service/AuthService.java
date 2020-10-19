package com.softplan.desafio.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.softplan.desafio.auth.payload.request.LoginRequest;
import com.softplan.desafio.auth.payload.request.SignupRequest;
import com.softplan.desafio.auth.payload.response.JwtResponse;
import com.softplan.desafio.auth.payload.response.MessageResponse;
import com.softplan.desafio.domain.model.Role;
import com.softplan.desafio.domain.model.RoleEnum;
import com.softplan.desafio.domain.model.User;
import com.softplan.desafio.repository.RoleRepository;
import com.softplan.desafio.repository.UserRepository;
import com.softplan.desafio.security.jwt.JwtUtils;
import com.softplan.desafio.security.service.UserDetailsImpl;

@Service
public class AuthService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	public ResponseEntity<?> signin(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	public ResponseEntity<String> refresh(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		String token = headerAuth.substring(7, headerAuth.length());
		String userName = jwtUtils.getUserNameFromJwtToken(token);
		String jwt = jwtUtils.generateJwtTokenByUserName(userName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + jwt);
		return ResponseEntity.ok().headers(headers).body("Autorizado com sucesso !");
	}

}
