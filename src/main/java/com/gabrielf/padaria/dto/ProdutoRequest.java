package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotNull(message = "Unidade é obrigatória")
        Produto.Unidade unidade,

        @NotNull @Positive(message = "Preço deve ser positivo")
        BigDecimal precoVenda,

        String descricao,

        @NotNull(message = "Categoria é obrigatória")
        Produto.Categoria categoria
) {}

