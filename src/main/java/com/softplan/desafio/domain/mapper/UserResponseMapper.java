package com.softplan.desafio.domain.mapper;

import org.springframework.stereotype.Component;

import com.softplan.desafio.api.payload.response.UserResponse;
import com.softplan.desafio.domain.model.User;

@Component
public class UserResponseMapper extends Mapper<User, UserResponse>{

}
