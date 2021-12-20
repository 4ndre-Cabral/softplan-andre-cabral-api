package com.softplan.desafio.api.controller;

import java.util.Arrays;
import java.util.List;

import com.softplan.desafio.api.payload.response.ProcedureResponse;
import com.softplan.desafio.domain.mapper.ProcedureResponseMapper;
import com.softplan.desafio.domain.model.Procedure;
import com.softplan.desafio.exception.NotFoundException;
import com.softplan.desafio.service.ProcedureService;
import com.softplan.desafio.util.ProcedureCreator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
// @SpringBootTest
public class ProcedureControllerTest {

    @InjectMocks // Quando quero testar a classe em si
    private ProcedureController procedureController;

    @Mock // Quando quero testar classes dentro da classe a ser testada
    private ProcedureService procedureService;

    @BeforeEach
    void setUp() throws NotFoundException {
        List<ProcedureResponse> procedureResponses = Arrays.asList(ProcedureCreator.createdProcedureResponse());
        BDDMockito.when(procedureService.findAll())
            .thenReturn(procedureResponses);

        ProcedureResponse procedureResponse = ProcedureCreator.createdProcedureResponse();
        BDDMockito.when(procedureService.findById(ArgumentMatchers.anyLong()))
            .thenReturn(procedureResponse);

        // Quando o método não retorna nada
        BDDMockito.doNothing().when(procedureService).notify();
    }

    @Test
    @DisplayName("List of procedures")
    void testFindAll() {
        String expectedDescription = ProcedureCreator.createdProcedureResponse().getDescription();
        List<ProcedureResponse> procedureResponses =  procedureController.findAll();

        Assertions.assertThat(procedureResponses).isNotNull();

        Assertions.assertThat(procedureResponses).isNotEmpty();
        Assertions.assertThat(procedureResponses).isNotEmpty().hasSize(1);
        
        Assertions.assertThat(procedureResponses.get(0).getDescription()).isEqualTo(expectedDescription);

    }

    @Test
    void testFindById() {
        ProcedureResponse procedureResponseExpected = ProcedureCreator.createdProcedureResponse();
        ProcedureResponse procedureResponse =  procedureController.findById(1L);

        Assertions.assertThat(procedureResponse).isNotNull();

        Assertions.assertThat(procedureResponse.getId()).isEqualTo(1L);
        
        Assertions.assertThat(procedureResponse.getDescription()).isEqualTo(procedureResponseExpected.getDescription());
    }

    @Test
    void testFindUnsignedProceduresByUserId() {

    }

    @Test
    void testProcedureRegister() {

    }

    @Test
    void testUpdateUserList() {

    }
}
