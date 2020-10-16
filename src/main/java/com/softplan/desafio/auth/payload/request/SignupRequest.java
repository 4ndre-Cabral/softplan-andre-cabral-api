package com.softplan.desafio.auth.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
 
@Setter @Getter
public class SignupRequest {
    @NotBlank(message = "O nome de usuário é obrigatório")
    @Size(min = 3, max = 20)
    private String username;
 
    @NotBlank(message = "O email é obrigatório")
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 40)
    private String password;
  
}
