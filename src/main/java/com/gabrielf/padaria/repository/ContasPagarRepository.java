package com.gabrielf.padaria.repository;

import com.gabrielf.padaria.model.ContasPagar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContasPagarRepository extends JpaRepository<ContasPagar, UUID> {

    List<ContasPagar> findByStatus(ContasPagar.Status status);
}

