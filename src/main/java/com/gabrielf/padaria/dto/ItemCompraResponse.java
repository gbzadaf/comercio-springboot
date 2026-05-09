package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.ItemCompra;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemCompraResponse(
        UUID id,
        UUID produtoId,
        String nomeProduto,
        BigDecimal quantidade,
        BigDecimal precoPago
) {
    public static ItemCompraResponse from(ItemCompra itemCompra) {
        return new ItemCompraResponse(
                itemCompra.getId(),
                itemCompra.getProduto().getId(),
                itemCompra.getProduto().getNome(),
                itemCompra.getQuantidade(),
                itemCompra.getPrecoPago()
        );
    }
}

