package com.gabrielf.padaria.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CompraRequest(
        @NotNull(message = "Fornecedor é obrigatório")
        UUID fornecedorId,

        @NotEmpty(message = "A compra deve ter pelo menos um item")
        List<ItemCompraRequest> itens
) {}

