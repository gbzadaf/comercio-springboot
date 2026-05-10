package com.gabrielf.padaria.controller;

import com.gabrielf.padaria.dto.ContasPagarRequest;
import com.gabrielf.padaria.dto.ContasPagarResponse;
import com.gabrielf.padaria.model.ContasPagar;
import com.gabrielf.padaria.services.ContasPagarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/contas-pagar")
public class ContasPagarController {

    private final ContasPagarService contasPagarService;

    public ContasPagarController(ContasPagarService contasPagarService) {
        this.contasPagarService = contasPagarService;
    }

    @PostMapping
    public ResponseEntity<ContasPagarResponse> create(@RequestBody @Valid ContasPagarRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contasPagarService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ContasPagarResponse>> findAll() {
        return ResponseEntity.ok(contasPagarService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContasPagarResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(contasPagarService.findById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ContasPagarResponse>> findByStatus(@PathVariable ContasPagar.Status status) {
        return ResponseEntity.ok(contasPagarService.findByStatus(status));
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<ContasPagarResponse> pagar(@PathVariable UUID id) {
        return ResponseEntity.ok(contasPagarService.pagar(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ContasPagarResponse> cancelar(@PathVariable UUID id) {
        return ResponseEntity.ok(contasPagarService.cancelar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        contasPagarService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
