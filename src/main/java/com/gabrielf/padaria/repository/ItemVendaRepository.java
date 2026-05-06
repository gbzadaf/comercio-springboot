package com.gabrielf.padaria.repository;

import com.gabrielf.padaria.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, UUID> {

}
