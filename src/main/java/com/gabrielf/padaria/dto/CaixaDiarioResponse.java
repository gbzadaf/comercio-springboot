package com.gabrielf.padaria.dto;

import com.gabrielf.padaria.model.CaixaDiario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CaixaDiarioResponse(
        UUID id,
        LocalDate data,
        BigDecimal totalVendas,
        BigDecimal totalDespesas,
        BigDecimal saldo,
        CaixaDiario.Status status
) {
    public static CaixaDiarioResponse from(CaixaDiario caixaDiario) {
        return new CaixaDiarioResponse(
                caixaDiario.getId(),
                caixaDiario.getData(),
                caixaDiario.getTotalVendas(),
                caixaDiario.getTotalDespesas(),
                caixaDiario.getSaldo(),
                caixaDiario.getStatus()
        );
    }
}

