package com.gabrielf.padaria.repository;

import com.gabrielf.padaria.model.CaixaDiario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CaixaDiarioRepository extends JpaRepository<CaixaDiario, UUID> {

    Optional<CaixaDiario> findByData(LocalDate data);
}
