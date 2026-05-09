package com.gabrielf.padaria.services;

import com.gabrielf.padaria.dto.ProdutoRequest;
import com.gabrielf.padaria.dto.ProdutoResponse;
import com.gabrielf.padaria.model.Produto;
import com.gabrielf.padaria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;


    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponse create(ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setNome(request.nome());
        produto.setUnidade(request.unidade());
        produto.setPrecoVenda(request.precoVenda());
        produto.setDescricao(request.descricao());
        produto.setCategoria(request.categoria());
        return ProdutoResponse.from(produtoRepository.save(produto));
    }

    public List<ProdutoResponse> findAll() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponse::from)
                .toList();
    }

    public ProdutoResponse findById(UUID id) {
        return ProdutoResponse.from(buscarOuFalhar(id));
    }

    public ProdutoResponse update(UUID id, ProdutoRequest request) {
        Produto existing = buscarOuFalhar(id);
        existing.setNome(request.nome());
        existing.setUnidade(request.unidade());
        existing.setPrecoVenda(request.precoVenda());
        existing.setDescricao(request.descricao());
        existing.setCategoria(request.categoria());
        return ProdutoResponse.from(produtoRepository.save(existing));
    }

    public void delete(UUID id) {
        buscarOuFalhar(id);
        produtoRepository.deleteById(id);
    }

    public Produto buscarOuFalhar(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

    }
}
