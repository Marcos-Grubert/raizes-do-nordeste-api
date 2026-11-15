package com.raizesdonordeste.empresa.dto;

import com.raizesdonordeste.empresa.model.Empresa;

import java.time.LocalDateTime;

public record EmpresaResponse(
        Long id,
        String razaoSocial,
        String nomeFantasia,
        String cnpj,
        Boolean ativa,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
    public static EmpresaResponse from(Empresa empresa) {
        return new EmpresaResponse(
                empresa.getId(),
                empresa.getRazaoSocial(),
                empresa.getNomeFantasia(),
                empresa.getCnpj(),
                empresa.getAtiva(),
                empresa.getCriadoEm(),
                empresa.getAtualizadoEm()
        );
    }
}
