package com.softplan.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softplan.desafio.domain.model.Procedure;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

}
