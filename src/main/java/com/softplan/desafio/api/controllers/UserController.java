package com.softplan.desafio.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.softplan.desafio.api.payload.request.UserRequest;
import com.softplan.desafio.api.payload.response.UserResponse;
import com.softplan.desafio.api.service.UserService;
import com.softplan.desafio.exception.NotFoundException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	@ApiOperation(value = "Listar todos usuários", authorizations = { @Authorization(value = "Admin") })
	@ApiResponses({ @ApiResponse(code = 401, message = "Acesso não autorizado."), })
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> findAll() {
		return userService.findAll();
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Listar por ID", authorizations = { @Authorization(value = "Admin") })
	@ApiResponses({
		@ApiResponse(code = 401, message = "Acesso não autorizado."),
		@ApiResponse(code = 404, message = "Não encontrado")
	})
	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse findById(@ApiParam(required = true) @PathVariable Long id) {
		try {
			return userService.findById(id);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value="Atualizar", authorizations = {@Authorization(value="Basic Authentication")})
	@ApiResponses({
		@ApiResponse(code = 401, message = "Acesso não autorizado."),
	})
	public UserResponse update(
			@ApiParam(required=true) @PathVariable Long id,
			@ApiParam(required=true) @RequestBody UserRequest dto) {
		try {
			return userService.update(id, dto);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
}
