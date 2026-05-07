package com.gabrielf.padaria.services;

import com.gabrielf.padaria.model.Fornecedor;
import com.gabrielf.padaria.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public Fornecedor create(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);

    }

    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll();

    }

    public Fornecedor findById(UUID id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));

    }

    public Fornecedor update(UUID id, Fornecedor fornecedor) {
        Fornecedor existing = findById(id);
        existing.setNome(fornecedor.getNome());
        existing.setTelefone(fornecedor.getTelefone());
        existing.setEmail(fornecedor.getEmail());
        existing.setCategoria(fornecedor.getCategoria());

        return fornecedorRepository.save(existing);
    }

    public void delete(UUID id) {
        findById(id);
        fornecedorRepository.deleteById(id);

    }
}
