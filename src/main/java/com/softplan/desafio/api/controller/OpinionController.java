package com.softplan.desafio.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.softplan.desafio.api.payload.request.OpinionRequest;
import com.softplan.desafio.service.OpinionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/opinions")
public class OpinionController {

	@Autowired
	OpinionService opinionService;
	
	@PostMapping
	@ApiOperation(value = "Cadastar um parecer", authorizations = { @Authorization(value = "Bearer Authentication") })
	@ApiResponses({
		@ApiResponse(code = 401, message = "Acesso não autorizado."),
		@ApiResponse(code = 400, message = "Solicitação inválida."),
		@ApiResponse(code = 500, message = "Erro interno.")
	})
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasRole('FINALIZADOR')")
	public ResponseEntity<?> opinionRegister(@Valid @RequestBody OpinionRequest dto) {
		return opinionService.add(dto);
		
	}
}
