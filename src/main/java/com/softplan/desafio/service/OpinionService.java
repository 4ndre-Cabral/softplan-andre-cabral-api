package com.softplan.desafio.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.softplan.desafio.api.payload.request.OpinionRequest;
import com.softplan.desafio.auth.payload.response.MessageResponse;
import com.softplan.desafio.domain.mapper.OpinionRequestMapper;
import com.softplan.desafio.domain.model.Opinion;
import com.softplan.desafio.repository.OpinionRepository;


@Service
public class OpinionService {
	
	@Autowired
	OpinionRepository opinionRepository;
	
	@Autowired
	OpinionRequestMapper opinionRequestMapper;
	
	public ResponseEntity<?> add(OpinionRequest dto) {
		Opinion opinion = opinionRequestMapper.dtoToDomain(dto);
		opinion.setDateRegister(OffsetDateTime.now());
		opinionRepository.save(opinion);
		return ResponseEntity.ok(new MessageResponse("Parecer cadastrado com sucesso!"));
	}

}
