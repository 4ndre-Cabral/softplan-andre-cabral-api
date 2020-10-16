package com.softplan.desafio.domain.mapper;

import org.springframework.stereotype.Component;

import com.softplan.desafio.api.payload.request.UserRequest;
import com.softplan.desafio.domain.model.User;

@Component
public class UserRequestMapper extends Mapper<User, UserRequest>{

}
