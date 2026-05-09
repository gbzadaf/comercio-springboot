package com.gabrielf.padaria.services;

import com.gabrielf.padaria.dto.CaixaDiarioResponse;
import com.gabrielf.padaria.model.CaixaDiario;
import com.gabrielf.padaria.repository.CaixaDiarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class CaixaDiarioService {

    private final CaixaDiarioRepository caixaDiarioRepository;


    public CaixaDiarioService(CaixaDiarioRepository caixaDiarioRepository) {
        this.caixaDiarioRepository = caixaDiarioRepository;
    }

    public CaixaDiario findOrCreateByData(LocalDate data) {
        return caixaDiarioRepository.findByData(data)
                .orElseGet(() -> {
                    CaixaDiario novoCaixa = new CaixaDiario();
                    novoCaixa.setData(data);
                    novoCaixa.setTotalVendas(BigDecimal.ZERO);
                    novoCaixa.setTotalDespesas(BigDecimal.ZERO);
                    novoCaixa.setSaldo(BigDecimal.ZERO);
                    novoCaixa.setStatus(CaixaDiario.Status.ABERTO);
                    return caixaDiarioRepository.save(novoCaixa);
                });
    }

    public List<CaixaDiarioResponse> findAll() {
        return caixaDiarioRepository.findAll()
                .stream()
                .map(CaixaDiarioResponse::from)
                .toList();
    }

    public CaixaDiarioResponse findById(UUID id) {
        return CaixaDiarioResponse.from(buscarOuFalhar(id));
    }

    @Transactional
    public void adicionarVenda(UUID id, BigDecimal valor) {
        CaixaDiario caixa = buscarOuFalhar(id);
        caixa.setTotalVendas(caixa.getTotalVendas().add(valor));
        caixa.setSaldo(caixa.getTotalVendas().subtract(caixa.getTotalDespesas()));
        caixaDiarioRepository.save(caixa);
    }

    @Transactional
    public void adicionarDespesa(UUID id, BigDecimal valor) {
        CaixaDiario caixa = buscarOuFalhar(id);
        caixa.setTotalDespesas(caixa.getTotalDespesas().add(valor));
        caixa.setSaldo(caixa.getTotalVendas().subtract(caixa.getTotalDespesas()));
        caixaDiarioRepository.save(caixa);
    }

    @Transactional
    public CaixaDiarioResponse fechar(UUID id) {
        CaixaDiario caixa = buscarOuFalhar(id);
        caixa.setStatus(CaixaDiario.Status.FECHADO);
        return CaixaDiarioResponse.from(caixaDiarioRepository.save(caixa));
    }

    private CaixaDiario buscarOuFalhar(UUID id) {
        return caixaDiarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caixa diário não encontrado"));
    }


}
