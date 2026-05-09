package com.gabrielf.padaria.services;

import com.gabrielf.padaria.dto.CompraRequest;
import com.gabrielf.padaria.dto.CompraResponse;
import com.gabrielf.padaria.model.Compra;
import com.gabrielf.padaria.model.Fornecedor;
import com.gabrielf.padaria.model.ItemCompra;
import com.gabrielf.padaria.model.Produto;
import com.gabrielf.padaria.repository.CompraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final FornecedorService fornecedorService;
    private final ProdutoService produtoService;
    private final EstoqueService estoqueService;

    public CompraService(CompraRepository compraRepository, FornecedorService fornecedorService,
                         ProdutoService produtoService, EstoqueService estoqueService) {
        this.compraRepository = compraRepository;
        this.fornecedorService = fornecedorService;
        this.produtoService = produtoService;
        this.estoqueService = estoqueService;
    }

    @Transactional
    public CompraResponse create(CompraRequest request) {
        Fornecedor fornecedor = fornecedorService.buscarOuFalhar(request.fornecedorId());

        Compra compra = new Compra();
        compra.setFornecedor(fornecedor);
        compra.setDataCompra(LocalDateTime.now());
        compra.setStatus(Compra.Status.PENDENTE);

        List<ItemCompra> itens = request.itens().stream().map(itemRequest -> {
            Produto produto = produtoService.buscarOuFalhar(itemRequest.produtoId());
            ItemCompra item = new ItemCompra();
            item.setCompra(compra);
            item.setProduto(produto);
            item.setQuantidade(itemRequest.quantidade());
            item.setPrecoPago(itemRequest.precoPago());
            return item;
        }).toList();

        compra.setItens(itens);
        compra.setTotal(calcularTotal(itens));

        Compra saved = compraRepository.save(compra);

        itens.forEach(item ->
                estoqueService.aumentarQuantidade(item.getProduto().getId(), item.getQuantidade())
        );

        return CompraResponse.from(saved);
    }

    public List<CompraResponse> findAll() {
        return compraRepository.findAll()
                .stream()
                .map(CompraResponse::from)
                .toList();
    }

    public CompraResponse findById(UUID id) {
        return CompraResponse.from(buscarOuFalhar(id));
    }

    @Transactional
    public CompraResponse pagar(UUID id) {
        Compra compra = buscarOuFalhar(id);
        compra.setStatus(Compra.Status.PAGA);
        return CompraResponse.from(compraRepository.save(compra));
    }

    @Transactional
    public CompraResponse cancelar(UUID id) {
        Compra compra = buscarOuFalhar(id);
        compra.setStatus(Compra.Status.CANCELADA);
        compra.getItens().forEach(item ->
                estoqueService.diminuirQuantidade(item.getProduto().getId(), item.getQuantidade())
        );
        return CompraResponse.from(compraRepository.save(compra));
    }

    private BigDecimal calcularTotal(List<ItemCompra> itens) {
        return itens.stream()
                .map(item -> item.getPrecoPago().multiply(item.getQuantidade()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Compra buscarOuFalhar(UUID id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada"));
    }

}
