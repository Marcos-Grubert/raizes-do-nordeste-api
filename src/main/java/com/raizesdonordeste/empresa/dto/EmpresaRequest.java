package com.raizesdonordeste.empresa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EmpresaRequest(
        @NotBlank(message = "Obrigatório informar o campo Razão Social.")
        @Size(max = 150, message = "O limite para o campo Razão Social é de 150 caracteres.")
        String razaoSocial,

        @NotBlank(message = "Obrigatório informar o campo Nome Fantasia.")
        @Size(max = 150, message = "O limite para o campo Nome Fantasia é de 150 caracteres.")
        String nomeFantasia,

        @NotBlank(message = "Obrigatório informar o campo CNPJ.")
        @Size(max = 14, message = "O limite para o campo CNPJ é de 14 caracteres.")
        String cnpj,

        @NotNull(message = "O campo de situação não pode ser nulo")
        Boolean ativa
) {
}
