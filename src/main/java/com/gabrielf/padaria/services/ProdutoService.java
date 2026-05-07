package com.gabrielf.padaria.services;

import com.gabrielf.padaria.model.Produto;
import com.gabrielf.padaria.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto create(Produto produto) {
        return produtoRepository.save(produto);

    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();

    }

    public Produto findById(UUID id) {

        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

    }

    public Produto update(UUID id, Produto produto) {
        Produto existing = findById(id);
        existing.setNome(produto.getNome());
        existing.setUnidade(produto.getUnidade());
        existing.setPrecoVenda(produto.getPrecoVenda());
        existing.setDescricao(produto.getDescricao());
        existing.setCategoria(produto.getCategoria());

        return produtoRepository.save(existing);
    }

    public void delete(UUID id) {
        findById(id);
        produtoRepository.deleteById(id);

    }
}
