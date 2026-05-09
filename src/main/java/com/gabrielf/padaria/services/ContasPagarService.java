package com.gabrielf.padaria.services;

import com.gabrielf.padaria.dto.ContasPagarRequest;
import com.gabrielf.padaria.dto.ContasPagarResponse;
import com.gabrielf.padaria.model.CaixaDiario;
import com.gabrielf.padaria.model.ContasPagar;
import com.gabrielf.padaria.model.Fornecedor;
import com.gabrielf.padaria.repository.ContasPagarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ContasPagarService {

    private final ContasPagarRepository contasPagarRepository;
    private final FornecedorService fornecedorService;
    private final CaixaDiarioService caixaDiarioService;

    public ContasPagarService(ContasPagarRepository contasPagarRepository, FornecedorService fornecedorService,
                              CaixaDiarioService caixaDiarioService) {
        this.contasPagarRepository = contasPagarRepository;
        this.fornecedorService = fornecedorService;
        this.caixaDiarioService = caixaDiarioService;
    }

    public ContasPagarResponse create(ContasPagarRequest request) {
        ContasPagar conta = new ContasPagar();
        conta.setDescricao(request.descricao());
        conta.setValor(request.valor());
        conta.setVencimento(request.vencimento());
        conta.setCategoria(request.categoria());
        conta.setStatus(ContasPagar.Status.PENDENTE);

        if (request.fornecedorId() != null) {
            Fornecedor fornecedor = fornecedorService.buscarOuFalhar(request.fornecedorId());
            conta.setFornecedor(fornecedor);
        }

        return ContasPagarResponse.from(contasPagarRepository.save(conta));
    }

    public List<ContasPagarResponse> findAll() {
        return contasPagarRepository.findAll()
                .stream()
                .map(ContasPagarResponse::from)
                .toList();
    }

    public ContasPagarResponse findById(UUID id) {
        return ContasPagarResponse.from(buscarOuFalhar(id));
    }

    public List<ContasPagarResponse> findByStatus(ContasPagar.Status status) {
        return contasPagarRepository.findByStatus(status)
                .stream()
                .map(ContasPagarResponse::from)
                .toList();
    }

    @Transactional
    public ContasPagarResponse pagar(UUID id) {
        ContasPagar conta = buscarOuFalhar(id);
        conta.setStatus(ContasPagar.Status.PAGA);
        conta.setDataPagamento(LocalDateTime.now());

        CaixaDiario caixa = caixaDiarioService.findOrCreateByData(LocalDate.now());
        caixaDiarioService.adicionarDespesa(caixa.getId(), conta.getValor());

        return ContasPagarResponse.from(contasPagarRepository.save(conta));
    }

    @Transactional
    public ContasPagarResponse cancelar(UUID id) {
        ContasPagar conta = buscarOuFalhar(id);
        conta.setStatus(ContasPagar.Status.CANCELADA);
        return ContasPagarResponse.from(contasPagarRepository.save(conta));
    }

    public void delete(UUID id) {
        buscarOuFalhar(id);
        contasPagarRepository.deleteById(id);
    }

    private ContasPagar buscarOuFalhar(UUID id) {
        return contasPagarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

}
