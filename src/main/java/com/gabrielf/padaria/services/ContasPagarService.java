package com.gabrielf.padaria.services;

import com.gabrielf.padaria.model.CaixaDiario;
import com.gabrielf.padaria.model.ContasPagar;
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
    private final CaixaDiarioService caixaDiarioService;

    public ContasPagarService(ContasPagarRepository contasPagarRepository, CaixaDiarioService caixaDiarioService) {
        this.contasPagarRepository = contasPagarRepository;
        this.caixaDiarioService = caixaDiarioService;
    }

    public ContasPagar create(ContasPagar contaPagar) {
        contaPagar.setStatus(ContasPagar.Status.PENDENTE);
        return contasPagarRepository.save(contaPagar);
    }

    public List<ContasPagar> findAll() {
        return contasPagarRepository.findAll();
    }

    public ContasPagar findById(UUID id) {
        return contasPagarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public List<ContasPagar> findByStatus(ContasPagar.Status status) {
        return contasPagarRepository.findByStatus(status);
    }

    @Transactional
    public ContasPagar pagar(UUID id) {
        ContasPagar conta = findById(id);
        conta.setStatus(ContasPagar.Status.PAGA);
        conta.setDataPagamento(LocalDateTime.now());

        CaixaDiario caixa = caixaDiarioService.findOrCreateByData(LocalDate.now());
        caixaDiarioService.adicionarDespesa(caixa.getId(), conta.getValor());

        return contasPagarRepository.save(conta);
    }

    @Transactional
    public ContasPagar cancelar(UUID id) {
        ContasPagar conta = findById(id);
        conta.setStatus(ContasPagar.Status.CANCELADA);
        return contasPagarRepository.save(conta);
    }

    public void delete(UUID id) {
        findById(id);
        contasPagarRepository.deleteById(id);
    }

}
