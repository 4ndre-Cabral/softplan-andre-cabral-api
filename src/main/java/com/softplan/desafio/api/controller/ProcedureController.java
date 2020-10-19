package com.softplan.desafio.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.softplan.desafio.api.payload.request.ProcedureRequest;
import com.softplan.desafio.api.payload.request.UserRequest;
import com.softplan.desafio.api.payload.response.ProcedureResponse;
import com.softplan.desafio.api.payload.response.UserResponse;
import com.softplan.desafio.exception.NotFoundException;
import com.softplan.desafio.service.ProcedureService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/procedures")
public class ProcedureController {

	@Autowired
	ProcedureService procedureService;
	
	@GetMapping
	@ApiOperation(value = "Listar todos os processos", authorizations = { @Authorization(value = "Bearer Authentication") })
	@ApiResponses({ @ApiResponse(code = 401, message = "Acesso não autorizado."), })
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('TRIADOR')")
	public List<ProcedureResponse> findAll() {
		return procedureService.findAll();
	}

	@GetMapping("unsigned/{userId}")
	@ApiOperation(value = "Listar todos os processos sem parecer pelo id do usuário ", authorizations = { @Authorization(value = "Bearer Authentication") })
	@ApiResponses({ @ApiResponse(code = 401, message = "Acesso não autorizado."), })
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('FINALIZADOR')")
	public List<ProcedureResponse> findUnsignedProceduresByUserId(@ApiParam(required=true) @PathVariable Long userId) {
		return procedureService.findUnsignedProceduresByUserId(userId);
	}
	
	@GetMapping("{id}")
	@ApiOperation(value = "Listar processo pelo id ", authorizations = { @Authorization(value = "Bearer Authentication") })
	@ApiResponses({ @ApiResponse(code = 401, message = "Acesso não autorizado."), })
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('TRIADOR')")
	public ProcedureResponse findById(@ApiParam(required=true) @PathVariable Long id) {
		try {
			return procedureService.findById(id);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PostMapping
	@ApiOperation(value = "Cadastrar novo processo", authorizations = { @Authorization(value = "Bearer Authentication") })
	@ApiResponses({ @ApiResponse(code = 401, message = "Acesso não autorizado."), })
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('TRIADOR')")
	public ProcedureResponse procedureRegister(@Valid @RequestBody ProcedureRequest dto) {
		return procedureService.add(dto);
		
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value="Atualizar lista de usuários de um processo", authorizations = {@Authorization(value="Bearer Authentication")})
	@ResponseStatus(HttpStatus.OK)
	@ApiResponses({
		@ApiResponse(code = 401, message = "Acesso não autorizado."),
	})
	@PreAuthorize("hasRole('TRIADOR')")
	public ProcedureResponse updateUserList(
			@ApiParam(required=true) @PathVariable Long id,
			@ApiParam(required=true) @RequestBody ProcedureRequest dto) {
		try {
			return procedureService.updateUserList(id, dto);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
}
