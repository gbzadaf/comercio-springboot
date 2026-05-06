package com.gabrielf.padaria.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "fornecedores")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String telefone;

    @Column
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    public enum Categoria {
        MERCADO,
        DISTRIBUIDORA,
        UTILIDADES,
        OUTROS
    }


}
