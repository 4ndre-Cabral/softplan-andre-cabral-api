package com.softplan.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softplan.desafio.domain.model.Procedure;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {

	@Query(value = 
			"SELECT p.* " +
			" FROM procedures p " +
			" LEFT JOIN procedure_opinion po ON p.id = po.procedure_id " +
			" WHERE po.user_id = :userId AND " +
			" NOT EXISTS (SELECT id FROM opinions o WHERE o.user_id = :userId AND o.procedure_id = p.id ) ",
			nativeQuery = true)
	List<Procedure> findUnsignedProceduresByUserId(@Param("userId") Long userId);
	
	List<Procedure> findAllByUserId(@Param("userId") Long userId);
}
