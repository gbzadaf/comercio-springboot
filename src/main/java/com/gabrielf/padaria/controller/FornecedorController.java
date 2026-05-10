package com.gabrielf.padaria.controller;

import com.gabrielf.padaria.dto.FornecedorRequest;
import com.gabrielf.padaria.dto.FornecedorResponse;
import com.gabrielf.padaria.services.FornecedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/fornecedores")
public class FornecedorController {


    private final FornecedorService fornecedorService;


    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public ResponseEntity<FornecedorResponse> create(@RequestBody @Valid FornecedorRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorResponse>> findAll() {
        return ResponseEntity.ok(fornecedorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponse> findById(@PathVariable UUID id) {

        return ResponseEntity.ok(fornecedorService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponse> update(@PathVariable UUID id, @RequestBody @Valid FornecedorRequest request) {

        return ResponseEntity.ok(fornecedorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {

        fornecedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

