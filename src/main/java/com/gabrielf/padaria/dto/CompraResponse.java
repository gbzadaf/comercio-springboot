package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.Compra;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CompraResponse(
        UUID id,
        UUID fornecedorId,
        String nomeFornecedor,
        LocalDateTime dataCompra,
        BigDecimal total,
        Compra.Status status,
        List<ItemCompraResponse> itens

) {
    public static CompraResponse from (Compra compra) {
        return new CompraResponse(
                compra.getId(),
                compra.getFornecedor().getId(),
                compra.getFornecedor().getNome(),
                compra.getDataCompra(),
                compra.getTotal(),
                compra.getStatus(),
                compra.getItens().stream().map(ItemCompraResponse::from).toList()
        );

    }
}

