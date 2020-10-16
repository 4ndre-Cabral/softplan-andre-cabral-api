package com.softplan.desafio.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.softplan.desafio.api.payload.request.UserRequest;
import com.softplan.desafio.api.payload.response.UserResponse;
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
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleEnum.ROLE_FINALIZADOR)
					.orElseThrow(() -> new RuntimeException("Privilégio não encontrada."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Privilégio não encontrada."));
					roles.add(adminRole);

					break;
				case "triad":
					Role modRole = roleRepository.findByName(RoleEnum.ROLE_TRIADOR)
							.orElseThrow(() -> new RuntimeException("Privilégio não encontrada."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(RoleEnum.ROLE_FINALIZADOR)
							.orElseThrow(() -> new RuntimeException("Privilégio não encontrada."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		user.setId(found.get().getId());
		user.setUsername(user.getUsername() == null ? found.get().getUsername() : user.getUsername());
		user.setEmail(user.getEmail() == null ? found.get().getEmail() : user.getEmail());
		user.setPassword(user.getPassword() == null ? found.get().getPassword() : encoder.encode(user.getPassword()));
		return userResponseMapper.domainToDto(userRepository.save(user));
	}
	
	public ResponseEntity<?> deleteById(Long id) throws NotFoundException {
		Optional<User> found = userRepository.findById(id);
		if(!found.isPresent())
			throw new NotFoundException();
		User user = found.get();
//		Set<Role> roles = user.getRoles();
//		roleRepository.deleteAll(roles);
		userRepository.delete(user);
		return ResponseEntity.ok(new MessageResponse("Usuário excluido com sucesso!"));
	}
}
