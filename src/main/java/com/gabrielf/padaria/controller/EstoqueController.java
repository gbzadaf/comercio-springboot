package com.gabrielf.padaria.controller;

import com.gabrielf.padaria.dto.EstoqueItemRequest;
import com.gabrielf.padaria.dto.EstoqueItemResponse;
import com.gabrielf.padaria.services.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public ResponseEntity<EstoqueItemResponse> create(@RequestBody @Valid EstoqueItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estoqueService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<EstoqueItemResponse>> findAll() {
        return ResponseEntity.ok(estoqueService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstoqueItemResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(estoqueService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueItemResponse> update(@PathVariable UUID id, @RequestBody @Valid EstoqueItemRequest
            request) {
        return ResponseEntity.ok(estoqueService.update(id, request));
    }
}
