package com.softplan.desafio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softplan.desafio.domain.model.Opinion;

@Repository
public interface OpinionRepository extends CrudRepository<Opinion, Long>{

}
