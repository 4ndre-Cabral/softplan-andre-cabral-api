package com.softplan.desafio.api.payload.request;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UserRequest {
	
	private Long id;
	
    private String username;
 
    private String email;
    
    private String password;
    
    private Set<String> role;
}
