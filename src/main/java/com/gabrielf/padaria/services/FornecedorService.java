package com.gabrielf.padaria.services;

import com.gabrielf.padaria.dto.FornecedorRequest;
import com.gabrielf.padaria.dto.FornecedorResponse;
import com.gabrielf.padaria.model.Fornecedor;
import com.gabrielf.padaria.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public FornecedorResponse create(FornecedorRequest request) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(request.nome());
        fornecedor.setTelefone(request.telefone());
        fornecedor.setEmail(request.email());
        fornecedor.setCategoria(request.categoria());
        return FornecedorResponse.from(fornecedorRepository.save(fornecedor));
    }

    public List<FornecedorResponse> findAll() {
        return fornecedorRepository.findAll()
                .stream()
                .map(FornecedorResponse::from)
                .toList();
    }

    public FornecedorResponse findById(UUID id) {
        return FornecedorResponse.from(buscarOuFalhar(id));
    }

    public FornecedorResponse update(UUID id, FornecedorRequest request) {
        Fornecedor existing = buscarOuFalhar(id);
        existing.setNome(request.nome());
        existing.setTelefone(request.telefone());
        existing.setEmail(request.email());
        existing.setCategoria(request.categoria());
        return FornecedorResponse.from(fornecedorRepository.save(existing));
    }

    public void delete(UUID id) {
        buscarOuFalhar(id);
        fornecedorRepository.deleteById(id);
    }

    public Fornecedor buscarOuFalhar(UUID id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));

    }
}
