package com.gabrielf.padaria.controller;

import com.gabrielf.padaria.dto.VendaRequest;
import com.gabrielf.padaria.dto.VendaResponse;
import com.gabrielf.padaria.services.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<VendaResponse> create(@RequestBody @Valid VendaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<VendaResponse>> findAll() {
        return ResponseEntity.ok(vendaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(vendaService.findById(id));
    }
}
