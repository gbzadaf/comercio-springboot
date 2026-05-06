package com.gabrielf.padaria.repository;

import com.gabrielf.padaria.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VendaRepository extends JpaRepository<Venda, UUID> {

    List<Venda> findByCaixaDiarioId(UUID caixaDiarioId);
}

