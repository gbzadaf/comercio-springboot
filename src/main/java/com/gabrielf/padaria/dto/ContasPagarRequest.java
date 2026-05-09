package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.ContasPagar;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ContasPagarRequest(

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        UUID fornecedorID,

        @NotNull(message = "Valor é obrigatório")
        BigDecimal valor,

        @NotNull(message = "Vencimento é obrigatório")
        LocalDate vencimento,

        @NotNull(message = "Categoria é obrigatória")
        ContasPagar.Categoria categoria
) {}

