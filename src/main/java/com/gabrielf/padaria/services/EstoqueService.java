package com.gabrielf.padaria.services;

import com.gabrielf.padaria.dto.EstoqueItemRequest;
import com.gabrielf.padaria.dto.EstoqueItemResponse;
import com.gabrielf.padaria.model.EstoqueItem;
import com.gabrielf.padaria.model.Produto;
import com.gabrielf.padaria.repository.EstoqueItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class EstoqueService {

    private final EstoqueItemRepository estoqueItemRepository;
    private final ProdutoService produtoService;

    public EstoqueService(EstoqueItemRepository estoqueItemRepository, ProdutoService produtoService) {
        this.estoqueItemRepository = estoqueItemRepository;
        this.produtoService = produtoService;

    }

    public EstoqueItemResponse create(EstoqueItemRequest request) {
        Produto produto = produtoService.buscarOuFalhar(request.produtoId());
        EstoqueItem item = new EstoqueItem();
        item.setProduto(produto);
        item.setQuantidade(request.quantidade());
        item.setQuantidadeMinima(request.quantidadeMinima());
        return EstoqueItemResponse.from(estoqueItemRepository.save(item));
    }

    public List<EstoqueItemResponse> findAll() {
        return estoqueItemRepository.findAll()
                .stream()
                .map(EstoqueItemResponse::from)
                .toList();
    }

    public EstoqueItemResponse findById(UUID id) {
        return EstoqueItemResponse.from(buscarOuFalhar(id));
    }

    public EstoqueItemResponse update(UUID id, EstoqueItemRequest request) {
        EstoqueItem existing = buscarOuFalhar(id);
        existing.setQuantidade(request.quantidade());
        existing.setQuantidadeMinima(request.quantidadeMinima());
        return EstoqueItemResponse.from(estoqueItemRepository.save(existing));
    }

    public void aumentarQuantidade(UUID produtoId, BigDecimal quantidade) {
        EstoqueItem item = buscarOuFalharPorProduto(produtoId);
        item.setQuantidade(item.getQuantidade().add(quantidade));
        estoqueItemRepository.save(item);
    }

    public void diminuirQuantidade(UUID produtoId, BigDecimal quantidade) {
        EstoqueItem item = buscarOuFalharPorProduto(produtoId);
        if (item.getQuantidade().compareTo(quantidade) < 0) {
            throw new RuntimeException("Estoque insuficiente para o produto: " + item.getProduto().getNome());
        }
        item.setQuantidade(item.getQuantidade().subtract(quantidade));
        estoqueItemRepository.save(item);
    }

    private EstoqueItem buscarOuFalhar(UUID id) {
        return estoqueItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item de estoque não encontrado"));
    }

    private EstoqueItem buscarOuFalharPorProduto(UUID produtoId) {
        return estoqueItemRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para esse produto"));
    }

}
