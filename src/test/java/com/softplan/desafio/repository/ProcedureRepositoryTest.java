package com.softplan.desafio.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import com.softplan.desafio.domain.model.Procedure;
import com.softplan.desafio.domain.model.User;
import com.softplan.desafio.util.ProcedureCreator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Tests for Procedure repository")
public class ProcedureRepositoryTest {

    @Autowired
    private ProcedureRepository procedureRepository;

    private Procedure procedure;

    @BeforeEach
    void createProcedure() {
        procedure = ProcedureCreator.createProcedureToBeSaved();
    }

    @Test
    @DisplayName("Save procedure when successful")
    void testSave() {
        Procedure procedureSaved = this.procedureRepository.save(procedure);
        Assertions.assertThat(procedureSaved).isNotNull();
        Assertions.assertThat(procedureSaved.getId()).isNotNull();
        Assertions.assertThat(procedureSaved.getDescription()).isEqualTo(procedure.getDescription());
    }

    @Test
    @DisplayName("Delete procedure when successful")
    void testDelete() {
        Procedure procedureSaved = this.procedureRepository.save(procedure);
        
        this.procedureRepository.delete(procedureSaved);
        
        Optional<Procedure> found = this.procedureRepository.findById(procedureSaved.getId());

        Assertions.assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Find all by user id when successful")
    void testFindAllByUserId() {
        Procedure procedureSaved = this.procedureRepository.save(procedure);
        
        List<Procedure> procedures = this.procedureRepository.findAllByUserId(procedureSaved.getUser().getId());

        Assertions.assertThat(procedures).isNotEmpty();
        Assertions.assertThat(procedures).contains(procedureSaved);
    }

    @Test
    void testFindUnsignedProceduresByUserId() {
        procedure.setDescription(null);

        // Assertions.assertThatThrownBy(() -> this.procedureRepository.save(procedure))
        //             .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.procedureRepository.save(procedure))
                .withMessageContaining("A descrição do processo é obrigatória");
    }

    @Test
    void testExceptionWhenDescriptionIsEmpty() {

    }

    @AfterEach
    void tearDown() {

    }
}
