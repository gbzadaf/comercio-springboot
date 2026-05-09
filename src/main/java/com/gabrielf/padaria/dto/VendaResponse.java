package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record VendaResponse(
        UUID id,
        LocalDateTime dataVenda,
        BigDecimal total,
        Venda.FormaPagamento formaPagamento,
        UUID caixaDiarioId,
        List<ItemVendaResponse> itens

) {
    public static VendaResponse from(Venda venda) {
        return new VendaResponse(
                venda.getId(),
                venda.getDataVenda(),
                venda.getTotal(),
                venda.getFormaPagamento(),
                venda.getCaixaDiario().getId(),
                venda.getItens().stream().map(ItemVendaResponse::from).toList()
        );
    }
}

