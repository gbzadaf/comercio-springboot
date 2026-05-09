package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.Fornecedor;

import java.util.UUID;

public record FornecedorResponse(
        UUID id,
        String nome,
        String telefone,
        String email,
        Fornecedor.Categoria categoria

) {
    public static FornecedorResponse from(Fornecedor fornecedor) {

        return new FornecedorResponse(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getTelefone(),
                fornecedor.getEmail(),
                fornecedor.getCategoria()
        );
    }
}

