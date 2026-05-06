package com.gabrielf.padaria.repository;

import com.gabrielf.padaria.model.ContasPagar;
import com.gabrielf.padaria.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContaPagarRepository extends JpaRepository<ContasPagar, UUID> {

    List<ContasPagar> findByStatus(ContasPagar.Status status);
}

