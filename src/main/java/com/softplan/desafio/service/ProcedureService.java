package com.softplan.desafio.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softplan.desafio.api.payload.request.ProcedureRequest;
import com.softplan.desafio.api.payload.response.ProcedureResponse;
import com.softplan.desafio.domain.mapper.ProcedureRequestMapper;
import com.softplan.desafio.domain.mapper.ProcedureResponseMapper;
import com.softplan.desafio.domain.model.Procedure;
import com.softplan.desafio.repository.ProcedureRepository;

@Service
public class ProcedureService {

	@Autowired
	ProcedureRepository procedureRepository;
	
	@Autowired
	ProcedureResponseMapper procedureResponseMapper;
	
	@Autowired
	ProcedureRequestMapper procedureRequestMapper;
	
	public List<ProcedureResponse> findAll() {
		return procedureResponseMapper.domainToDto(procedureRepository.findAll());
	}
	
	public ProcedureResponse add(ProcedureRequest dto) {
		Procedure procedure = procedureRequestMapper.dtoToDomain(dto);
		procedure.setRegisterDate(OffsetDateTime.now());
		procedureRepository.save(procedure);
		return procedureResponseMapper.domainToDto(procedureRepository.save(procedure));
	}
}
