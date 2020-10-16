package com.softplan.desafio.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.softplan.desafio.api.payload.request.ProcedureRequest;
import com.softplan.desafio.api.payload.response.ProcedureResponse;
import com.softplan.desafio.api.payload.response.UserResponse;
import com.softplan.desafio.service.ProcedureService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/procedures")
public class ProcedureController {

	@Autowired
	ProcedureService procedureService;
	
	@GetMapping
	@ApiOperation(value = "Listar todos os processos", authorizations = { @Authorization(value = "Usuário triador e finalizador") })
	@ApiResponses({ @ApiResponse(code = 401, message = "Acesso não autorizado."), })
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('FINALIZADOR') or hasRole('TRIADOR')")
	public List<ProcedureResponse> findAll() {
		return procedureService.findAll();
	}
	
	@PostMapping
	@ApiOperation(value = "Listar todos os processos", authorizations = { @Authorization(value = "Usuário triador") })
	@ApiResponses({ @ApiResponse(code = 401, message = "Acesso não autorizado."), })
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('TRIADOR')")
	public ProcedureResponse  userRegister(@Valid @RequestBody ProcedureRequest dto) {
		return procedureService.add(dto);
		
	}
}
