package com.gabrielf.padaria.services;

import com.gabrielf.padaria.model.CaixaDiario;
import com.gabrielf.padaria.model.ItemVenda;
import com.gabrielf.padaria.model.Venda;
import com.gabrielf.padaria.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final EstoqueService estoqueService;
    private final CaixaDiarioService caixaDiarioService;

    public VendaService(VendaRepository vendaRepository, EstoqueService estoqueService,
                        CaixaDiarioService caixaDiarioService) {
        this.vendaRepository = vendaRepository;
        this.estoqueService = estoqueService;
        this.caixaDiarioService = caixaDiarioService;
    }

    @Transactional
    public Venda create(Venda venda) {
        CaixaDiario caixa = caixaDiarioService.findOrCreateByData(LocalDate.now());

        venda.setDataVenda(LocalDateTime.now());
        venda.setCaixaDiario(caixa);
        venda.getItens().forEach(item -> item.setSubtotal(
                item.getPrecoUnitario().multiply(item.getQuantidade())
        ));
        venda.setTotal(calcularTotal(venda.getItens()));

        Venda saved = vendaRepository.save(venda);

        saved.getItens().forEach(item ->
                estoqueService.diminuirQuantidade(item.getProduto().getId(), item.getQuantidade())
        );

        caixaDiarioService.adicionarVenda(caixa.getId(), saved.getTotal());

        return saved;
    }

    public List<Venda> findAll() {
        return vendaRepository.findAll();
    }

    public Venda findById(UUID id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

    private BigDecimal calcularTotal(List<ItemVenda> itens) {
        return itens.stream()
                .map(ItemVenda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
