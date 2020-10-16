package com.softplan.desafio.api.mapper;

import org.springframework.stereotype.Component;

import com.softplan.desafio.api.payload.response.RoleResponse;
import com.softplan.desafio.models.Role;

@Component
public class RoleResponseMapper extends Mapper<Role, RoleResponse>{

}
