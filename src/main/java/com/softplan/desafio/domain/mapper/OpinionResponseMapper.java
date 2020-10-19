package com.softplan.desafio.domain.mapper;

import org.springframework.stereotype.Component;

import com.softplan.desafio.api.payload.response.OpinionResponse;
import com.softplan.desafio.domain.model.Opinion;

@Component
public class OpinionResponseMapper extends Mapper<Opinion, OpinionResponse> {

}
