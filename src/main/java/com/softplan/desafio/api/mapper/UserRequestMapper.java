package com.softplan.desafio.api.mapper;

import org.springframework.stereotype.Component;

import com.softplan.desafio.api.payload.request.UserRequest;
import com.softplan.desafio.models.User;

@Component
public class UserRequestMapper extends Mapper<User, UserRequest>{

}
