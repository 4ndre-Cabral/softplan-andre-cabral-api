package com.softplan.desafio.api.payload.request;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.softplan.desafio.domain.model.Opinion;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProcedureRequest {

	@NotBlank(message = "A descrição do processo é obrigatória")
	@Size(max = 255)
	private String description;
	
	@NotNull
	private UserRequest user;
	
	private List<Opinion> opinions;
	
	private Set<UserRequest> users = new HashSet<>();
}
