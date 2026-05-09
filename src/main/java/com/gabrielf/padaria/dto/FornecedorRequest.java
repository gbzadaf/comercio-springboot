package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.Fornecedor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FornecedorRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        String telefone,

        @Email( message = "Email invalído")
        String email,

        @NotNull(message = "Categoria é obrigatória")
        Fornecedor.Categoria categoria
) {}



