package com.gabrielf.padaria.services;

import com.gabrielf.padaria.model.Compra;
import com.gabrielf.padaria.model.ItemCompra;
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
    private final EstoqueService estoqueService;


    public CompraService(CompraRepository compraRepository, EstoqueService estoqueService) {
        this.compraRepository = compraRepository;
        this.estoqueService = estoqueService;
    }

    @Transactional
    public Compra create(Compra compra) {
        compra.setDataCompra(LocalDateTime.now());
        compra.setStatus(Compra.Status.PENDENTE);
        compra.setTotal(calcularTotal(compra.getItens()));

        Compra saved = compraRepository.save(compra);

        saved.getItens().forEach(item ->
                estoqueService.aumentarQuantidade(item.getProduto().getId(), item.getQuantidade()));

        return saved;
    }

    public List<Compra> findAll() {
        return compraRepository.findAll();
    }

    public Compra findById(UUID id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra não encontrada"));

    }

    @Transactional
    public Compra cancelar(UUID id) {
        Compra compra = findById(id);
        compra.setStatus(Compra.Status.CANCELADA);
        compra.getItens().forEach(item ->
                estoqueService.diminuirQuantidade(item.getProduto().getId(), item.getQuantidade()));

        return compraRepository.save(compra);
    }


    private BigDecimal calcularTotal(List<ItemCompra> itens) {
        return itens.stream()
                .map(item -> item.getPrecoPago().multiply(item.getQuantidade()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
