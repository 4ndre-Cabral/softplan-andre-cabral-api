package com.softplan.desafio.api.mapper;

import org.springframework.stereotype.Component;

import com.softplan.desafio.api.payload.response.UserResponse;
import com.softplan.desafio.models.User;

@Component
public class UserResponseMapper extends Mapper<User, UserResponse>{

}
