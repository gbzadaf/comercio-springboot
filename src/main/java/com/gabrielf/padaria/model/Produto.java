package com.gabrielf.padaria.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unidade unidade;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Column
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    public enum Unidade {
        UNIDADE,
        KILO,
        CAIXA,
        PACOTE,
        LITRO
    }

    public enum Categoria {
        MATERIA_PRIMA,   // farinha, ovos, manteiga...
        REVENDA,         // presunto, queijo, refrigerante...
        PRODUZIDO        // pão, salgado, bolo...
    }
}
