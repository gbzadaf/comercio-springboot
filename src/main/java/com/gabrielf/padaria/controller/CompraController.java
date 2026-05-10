package com.gabrielf.padaria.controller;

import com.gabrielf.padaria.dto.CompraRequest;
import com.gabrielf.padaria.dto.CompraResponse;
import com.gabrielf.padaria.services.CompraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;

    }

    @PostMapping
    public ResponseEntity<CompraResponse> create(@RequestBody @Valid CompraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compraService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CompraResponse>> findAll() {
        return ResponseEntity.ok(compraService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(compraService.findById(id));
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<CompraResponse> pagar(@PathVariable UUID id) {
        return ResponseEntity.ok(compraService.pagar(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<CompraResponse> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(compraService.cancelar(id));
    }
}
