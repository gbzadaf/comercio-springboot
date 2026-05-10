package com.gabrielf.padaria.controller;

import com.gabrielf.padaria.dto.CaixaDiarioResponse;
import com.gabrielf.padaria.services.CaixaDiarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/caixa-diario")
public class CaixaDiarioController {

    private final CaixaDiarioService caixaDiarioService;

    public CaixaDiarioController(CaixaDiarioService caixaDiarioService) {
        this.caixaDiarioService = caixaDiarioService;
    }

    @GetMapping
    public ResponseEntity<List<CaixaDiarioResponse>> findAll() {
        return ResponseEntity.ok(caixaDiarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaixaDiarioResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(caixaDiarioService.findById(id));
    }

    @PatchMapping("/{id}/fechar")
    public ResponseEntity<CaixaDiarioResponse> fechar(@PathVariable UUID id) {
        return ResponseEntity.ok(caixaDiarioService.fechar(id));
    }

}
