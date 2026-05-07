package com.gabrielf.padaria.repository;

import com.gabrielf.padaria.model.EstoqueItem;
import com.gabrielf.padaria.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstoqueItemRepository extends JpaRepository<EstoqueItem, UUID> {

    Optional<EstoqueItem> findByProdutoId(UUID produtoId);

}
