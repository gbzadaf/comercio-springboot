package com.gabrielf.padaria.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemVendaRequest(
        @NotNull(message = "Produto é obrigatório")
        UUID produtoId,

        @NotNull @Positive(message = "Quantidade deve ser positiva")
        BigDecimal quantidade,

        @NotNull @Positive(message = "Preço deve ser positivo")
        BigDecimal precoUnitario
) {}

