package com.gabrielf.padaria.controller;

import com.gabrielf.padaria.dto.ProdutoRequest;
import com.gabrielf.padaria.dto.ProdutoResponse;
import com.gabrielf.padaria.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;


    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> create(@RequestBody @Valid ProdutoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> findAll() {

        return ResponseEntity.ok(produtoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable UUID id) {

        return ResponseEntity.ok(produtoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> update(@PathVariable UUID id, @RequestBody @Valid ProdutoRequest request) {

        return ResponseEntity.ok(produtoService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable UUID id) {

        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
