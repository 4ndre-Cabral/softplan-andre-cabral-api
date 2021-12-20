package com.softplan.desafio.api.payload.response;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.softplan.desafio.domain.model.Opinion;
import com.softplan.desafio.domain.model.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter @Builder
public class ProcedureResponse {

	private Long id;
	
	private String description;
	
	private OffsetDateTime dateRegiter;
	
	private List<OpinionResponse> opinions;
	
	private Set<UserResponse> users;
}
