package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.ItemVenda;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemVendaResponse(
        UUID id,
        UUID produtoId,
        String nomeProduto,
        BigDecimal quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal

) {
    public static ItemVendaResponse from(ItemVenda itemVenda) {
        return  new ItemVendaResponse(
                itemVenda.getId(),
                itemVenda.getProduto().getId(),
                itemVenda.getProduto().getNome(),
                itemVenda.getQuantidade(),
                itemVenda.getPrecoUnitario(),
                itemVenda.getSubtotal()
        );
    }
}

