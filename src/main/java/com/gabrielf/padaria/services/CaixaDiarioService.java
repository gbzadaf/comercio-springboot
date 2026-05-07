package com.gabrielf.padaria.services;

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

    public List<CaixaDiario> findAll() {
        return caixaDiarioRepository.findAll();
    }

    public CaixaDiario findById(UUID id) {
        return caixaDiarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caixa diário não encontrado"));
    }

    @Transactional
    public void adicionarVenda(UUID id, BigDecimal valor) {
        CaixaDiario caixa = findById(id);
        caixa.setTotalVendas(caixa.getTotalVendas().add(valor));
        caixa.setSaldo(caixa.getTotalVendas().subtract(caixa.getTotalDespesas()));
        caixaDiarioRepository.save(caixa);
    }

    @Transactional
    public void adicionarDespesa(UUID id, BigDecimal valor) {
        CaixaDiario caixa = findById(id);
        caixa.setTotalDespesas(caixa.getTotalDespesas().add(valor));
        caixa.setSaldo(caixa.getTotalVendas().subtract(caixa.getTotalDespesas()));
        caixaDiarioRepository.save(caixa);
    }

    @Transactional
    public CaixaDiario fechar(UUID id) {
        CaixaDiario caixa = findById(id);
        caixa.setStatus(CaixaDiario.Status.FECHADO);
        return caixaDiarioRepository.save(caixa);
    }


}
