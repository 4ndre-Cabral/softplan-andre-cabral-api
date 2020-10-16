package com.softplan.desafio.api.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.softplan.desafio.domain.model.Procedure;
import com.softplan.desafio.domain.model.User;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class OpinionRequest {

	@NotBlank(message = "A descrição do parecer é obrigatória")
	private String description;
	
	@NotNull
	private User user;
	
	@NotNull
	private Procedure process;
}
