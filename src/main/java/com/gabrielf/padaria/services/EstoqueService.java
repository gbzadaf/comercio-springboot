package com.gabrielf.padaria.services;

import com.gabrielf.padaria.model.EstoqueItem;
import com.gabrielf.padaria.model.Produto;
import com.gabrielf.padaria.repository.EstoqueItemRepository;
import com.gabrielf.padaria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class EstoqueService {

    private final EstoqueItemRepository estoqueItemRepository;


    public EstoqueService(EstoqueItemRepository estoqueItemRepository) {
        this.estoqueItemRepository = estoqueItemRepository;

    }

    public EstoqueItem create(EstoqueItem estoqueItem) {

        return estoqueItemRepository.save(estoqueItem);
    }

    public List<EstoqueItem> findAll() {

        return estoqueItemRepository.findAll();
    }

    public EstoqueItem findById(UUID id) {

        return estoqueItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de estoque não encontrado"));
    }

    public EstoqueItem findByProduto(UUID produtoId) {

        return estoqueItemRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para esse produto"));

    }

    public void aumentarQuantidade(UUID produtoID, BigDecimal quantidade) {

        EstoqueItem item = findByProduto(produtoID);
        item.setQuantidade(item.getQuantidade().add(quantidade));
        estoqueItemRepository.save(item);
    }

    public void diminuirQuantidade(UUID produtoId, BigDecimal quantidade) {

        EstoqueItem item = findByProduto(produtoId);
        if (item.getQuantidade().compareTo(quantidade) < 0) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + item.getProduto().getNome());
        }

        item.setQuantidade(item.getQuantidade().subtract(quantidade));
        estoqueItemRepository.save(item);
    }

    public boolean isEstoqueAbaixoDoMinimo(UUID produtoId) {

        EstoqueItem item = findByProduto(produtoId);
        return item.getQuantidade().compareTo(item.getQuantidadeMinima()) < 0;

    }

    public EstoqueItem update(UUID id, EstoqueItem estoqueItem) {
        EstoqueItem existing = findById(id);
        existing.setQuantidade(estoqueItem.getQuantidade());
        existing.setQuantidadeMinima(estoqueItem.getQuantidadeMinima());

        return estoqueItemRepository.save(existing);
    }

}
