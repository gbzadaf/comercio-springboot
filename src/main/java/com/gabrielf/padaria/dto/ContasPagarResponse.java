package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.ContasPagar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ContasPagarResponse(
        UUID id,
        String descricao,
        UUID fornecedorId,
        String nomeFornecedor,
        BigDecimal valor,
        LocalDate vencimento,
        LocalDateTime dataPagamento,
        ContasPagar.Status status,
        ContasPagar.Categoria categoria

) {
    public static ContasPagarResponse from(ContasPagar contasPagar) {
        return new ContasPagarResponse(
                contasPagar.getId(),
                contasPagar.getDescricao(),
                contasPagar.getFornecedor() != null ? contasPagar.getFornecedor().getId() : null,
                contasPagar.getFornecedor() != null ? contasPagar.getFornecedor().getNome() : null,
                contasPagar.getValor(),
                contasPagar.getVencimento(),
                contasPagar.getDataPagamento(),
                contasPagar.getStatus(),
                contasPagar.getCategoria()
        );
    }
}

