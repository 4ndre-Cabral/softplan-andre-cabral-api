package com.softplan.desafio.api.payload.response;

import com.softplan.desafio.domain.model.RoleEnum;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RoleResponse {

	private RoleEnum name;
}
