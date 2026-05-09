package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.Produto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoResponse (
    UUID id,
    String nome,
    Produto.Unidade unidade,
    BigDecimal precoVenda,
    String descricao,
    Produto.Categoria categoria

) {
    public static ProdutoResponse from (Produto produto) {
        return  new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getUnidade(),
                produto.getPrecoVenda(),
                produto.getDescricao(),
                produto.getCategoria()
        );
    }
}
