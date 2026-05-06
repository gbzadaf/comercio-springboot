package com.gabrielf.padaria.repository;

import com.gabrielf.padaria.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompraRepository extends JpaRepository<Compra, UUID> {

}
