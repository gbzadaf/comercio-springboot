package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.EstoqueItem;

import java.math.BigDecimal;
import java.util.UUID;

public record EstoqueItemResponse(
        UUID id,
        UUID produtoId,
        String nomeProduto,
        BigDecimal quantidade,
        BigDecimal quantidadeMinima,
        boolean abaixoDoMinimo
) {
    public static EstoqueItemResponse from (EstoqueItem estoqueItem) {
        return new EstoqueItemResponse(
                estoqueItem.getId(),
                estoqueItem.getProduto().getId(),
                estoqueItem.getProduto().getNome(),
                estoqueItem.getQuantidade(),
                estoqueItem.getQuantidadeMinima(),
                estoqueItem.getQuantidade().compareTo(estoqueItem.getQuantidadeMinima()) < 0
        );
    }
}

