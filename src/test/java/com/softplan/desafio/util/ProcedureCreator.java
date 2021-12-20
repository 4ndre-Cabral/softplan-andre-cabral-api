package com.softplan.desafio.util;

import java.time.OffsetDateTime;

import com.softplan.desafio.api.payload.response.ProcedureResponse;
import com.softplan.desafio.domain.model.Procedure;
import com.softplan.desafio.domain.model.User;

public class ProcedureCreator {
    
    private static User createUser() {
        return User.builder()
                            .id(1L)
                            .email("teste@teste.com")
                            .password("password")
                            .build();
    }
    
    public static Procedure createProcedureToBeSaved() {
        return Procedure.builder()
                        .description("description")
                        .dateRegiter(OffsetDateTime.now())
                        .user(createUser())
                        .build();
    }
    
    public static Procedure createdProcedure() {
        return Procedure.builder()
                        .id(1L)
                        .description("description")
                        .dateRegiter(OffsetDateTime.now())
                        .user(createUser())
                        .build();
    }
    
    public static ProcedureResponse createdProcedureResponse() {
        return ProcedureResponse.builder()
                        .id(1L)
                        .description("description")
                        .dateRegiter(OffsetDateTime.now())
                        .build();
    }
}
