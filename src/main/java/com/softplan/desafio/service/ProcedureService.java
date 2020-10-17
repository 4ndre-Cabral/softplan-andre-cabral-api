package com.softplan.desafio.service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softplan.desafio.api.payload.request.ProcedureRequest;
import com.softplan.desafio.api.payload.request.UserRequest;
import com.softplan.desafio.api.payload.response.ProcedureResponse;
import com.softplan.desafio.domain.mapper.ProcedureRequestMapper;
import com.softplan.desafio.domain.mapper.ProcedureResponseMapper;
import com.softplan.desafio.domain.model.Procedure;
import com.softplan.desafio.domain.model.User;
import com.softplan.desafio.exception.NotFoundException;
import com.softplan.desafio.repository.ProcedureRepository;
import com.softplan.desafio.repository.UserRepository;

@Service
public class ProcedureService {

	@Autowired
	ProcedureRepository procedureRepository;
	
	@Autowired
	ProcedureResponseMapper procedureResponseMapper;
	
	@Autowired
	ProcedureRequestMapper procedureRequestMapper;
	
	@Autowired
	UserRepository userRepository;
	
	public List<ProcedureResponse> findAll() {
		return procedureResponseMapper.domainToDto(procedureRepository.findAll());
	}
	
	public List<ProcedureResponse> findUnsignedProceduresByUserId(Long userId) {
		return procedureResponseMapper.domainToDto(procedureRepository.findUnsignedProceduresByUserId(userId));
	}
	
	public ProcedureResponse add(ProcedureRequest dto) {
		Procedure procedure = procedureRequestMapper.dtoToDomain(dto);
		procedure.setUsers(this.getUsersFromProcedure(dto));
		procedure.setDateRegiter(OffsetDateTime.now());
		return procedureResponseMapper.domainToDto(procedureRepository.save(procedure));
	}

	public ProcedureResponse updateUserList(Long id, ProcedureRequest dto) throws NotFoundException {
		Optional<Procedure> procedureFound = procedureRepository.findById(id);
		if(!procedureFound.isPresent())
			throw new NotFoundException();
		Procedure procedure = procedureFound.get();
		procedure.setUsers(this.getUsersFromProcedure(dto));
		return procedureResponseMapper.domainToDto(procedureRepository.save(procedure));
	}
	
	public Set<User> getUsersFromProcedure (ProcedureRequest dto) {
		Set<User> users = new HashSet<>();
		//Elimina usuarios inexistentes
		for (UserRequest userRequest : dto.getUsers()) {
			Optional<User> found = userRepository.findById(userRequest.getId());
			if(found.isPresent())
				users.add(found.get());
		}
		return users;
	}
}
