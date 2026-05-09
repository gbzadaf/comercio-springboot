package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.Venda;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record VendaRequest(
        @NotNull(message = "Forma de pagamento é obrigatória")
        Venda.FormaPagamento formaPagamento,

        @NotEmpty(message = "A venda deve ter pelo menos um item")
        List<ItemVendaRequest> itens
) {}

