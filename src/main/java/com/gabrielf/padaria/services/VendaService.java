package com.gabrielf.padaria.services;

import com.gabrielf.padaria.dto.VendaRequest;
import com.gabrielf.padaria.dto.VendaResponse;
import com.gabrielf.padaria.model.CaixaDiario;
import com.gabrielf.padaria.model.ItemVenda;
import com.gabrielf.padaria.model.Produto;
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
    private final ProdutoService produtoService;
    private final EstoqueService estoqueService;
    private final CaixaDiarioService caixaDiarioService;

    public VendaService(VendaRepository vendaRepository, ProdutoService produtoService, EstoqueService estoqueService,
                        CaixaDiarioService caixaDiarioService) {
        this.vendaRepository = vendaRepository;
        this.produtoService = produtoService;
        this.estoqueService = estoqueService;
        this.caixaDiarioService = caixaDiarioService;
    }

    public VendaResponse create(VendaRequest request) {
        CaixaDiario caixa = caixaDiarioService.findOrCreateByData(LocalDate.now());

        Venda venda = new Venda();
        venda.setDataVenda(LocalDateTime.now());
        venda.setFormaPagamento(request.formaPagamento());
        venda.setCaixaDiario(caixa);

        List<ItemVenda> itens = request.itens().stream().map(itemRequest -> {
            Produto produto = produtoService.buscarOuFalhar(itemRequest.produtoId());
            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setProduto(produto);
            item.setQuantidade(itemRequest.quantidade());
            item.setPrecoUnitario(itemRequest.precoUnitario());
            item.setSubtotal(itemRequest.precoUnitario().multiply(itemRequest.quantidade()));
            return item;
        }).toList();

        venda.setItens(itens);
        venda.setTotal(calcularTotal(itens));

        Venda saved = vendaRepository.save(venda);

        itens.forEach(item ->
                estoqueService.diminuirQuantidade(item.getProduto().getId(), item.getQuantidade())
        );

        caixaDiarioService.adicionarVenda(caixa.getId(), saved.getTotal());

        return VendaResponse.from(saved);
    }

    public List<VendaResponse> findAll() {
        return vendaRepository.findAll()
                .stream()
                .map(VendaResponse::from)
                .toList();
    }

    public VendaResponse findById(UUID id) {
        return VendaResponse.from(buscarOuFalhar(id));
    }

    private BigDecimal calcularTotal(List<ItemVenda> itens) {
        return itens.stream()
                .map(ItemVenda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Venda buscarOuFalhar(UUID id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

}
