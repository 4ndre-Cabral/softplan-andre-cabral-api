package com.softplan.desafio.api.payload.response;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserResponse {
	
	private Long id;

    private String username;
 
    private String email;
    
    private Set<RoleResponse> roles;
}
