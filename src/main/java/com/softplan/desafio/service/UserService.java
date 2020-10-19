package com.softplan.desafio.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.softplan.desafio.api.payload.request.UserRequest;
import com.softplan.desafio.api.payload.response.UserResponse;
import com.softplan.desafio.auth.payload.request.SignupRequest;
import com.softplan.desafio.auth.payload.response.MessageResponse;
import com.softplan.desafio.domain.mapper.UserRequestMapper;
import com.softplan.desafio.domain.mapper.UserResponseMapper;
import com.softplan.desafio.domain.model.Role;
import com.softplan.desafio.domain.model.RoleEnum;
import com.softplan.desafio.domain.model.User;
import com.softplan.desafio.exception.NotFoundException;
import com.softplan.desafio.repository.RoleRepository;
import com.softplan.desafio.repository.UserRepository;


@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserResponseMapper userResponseMapper;
	
	@Autowired
	UserRequestMapper userRequestMapper;
	
	@Autowired
	PasswordEncoder encoder;
	
	public List<UserResponse> findAll() {
		return userResponseMapper.domainToDto(userRepository.findAll());
	}
	
	public UserResponse findById(Long id) throws NotFoundException {
		Optional<User> found = userRepository.findById(id);
		if(!found.isPresent())
			throw new NotFoundException();
		
		return userResponseMapper.domainToDto(found.get());
	}
	
	public UserResponse update(Long id, UserRequest userDTO) throws NotFoundException {
		Optional<User> found = userRepository.findById(id);
		if(!found.isPresent())
			throw new NotFoundException();
		
		User user = userRequestMapper.dtoToDomain(userDTO);
		
		Set<String> strRoles = userDTO.getRole();

		user.setRoles(this.getRoles(strRoles));
		user.setId(found.get().getId());
		user.setUsername(user.getUsername() == null ? found.get().getUsername() : user.getUsername());
		user.setEmail(user.getEmail() == null ? found.get().getEmail() : user.getEmail());
		user.setPassword(user.getPassword() == null ? found.get().getPassword() : encoder.encode(user.getPassword()));
		return userResponseMapper.domainToDto(userRepository.save(user));
	}

	public ResponseEntity<?> register(@Valid SignupRequest signUpRequest) {
		
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Email já em uso!"));
		}

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Nome de usuário já está em uso!"));
		}

		// Create new user
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();

		user.setRoles(this.getRoles(strRoles));
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("Usuário cadastrado com sucesso!"));
	}
	
	public ResponseEntity<?> deleteById(Long id) throws NotFoundException {
		Optional<User> found = userRepository.findById(id);
		if(!found.isPresent())
			throw new NotFoundException();
		User user = found.get();
		userRepository.delete(user);
		return ResponseEntity.ok(new MessageResponse("Usuário excluido com sucesso!"));
	}
	
	/**
	 * Get valid roles by roles string array
	 * @param strRoles: Set of string roles
	 * @return valid roles or ROLE_FINALIZADOR if not valid string roles are delivered
	 */
	public Set<Role> getRoles(Set<String> strRoles) {
		Set<Role> roles = new HashSet<>();
		strRoles.forEach(role -> {
			RoleEnum currentRoleEnum = RoleEnum.forName(role);
			if (currentRoleEnum != null) {	// If role exist		
				Optional<Role> foundRole = roleRepository.findByName(RoleEnum.valueOf(role));
				if (foundRole.isPresent())
					roles.add(foundRole.get());
			}
		});
		
		if(roles.size() == 0) {
			Role currentRole = roleRepository.findByName(RoleEnum.ROLE_FINALIZADOR)
					.orElseThrow(() -> new RuntimeException("Privilégio não encontrado."));
			roles.add(currentRole);
		}
		return roles;
	}
}
