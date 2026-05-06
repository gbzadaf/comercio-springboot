package com.gabrielf.padaria.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "contas_pagar")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContasPagar {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate vencimento;

    @Column
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    public enum Status {
        PENDENTE,
        PAGA,
        VENCIDA,
        CANCELADA
    }

    public enum Categoria {
        COMPRA,       // compras de mercado/distribuidora
        AGUA,
        LUZ,
        ALUGUEL,
        OUTROS
    }
}
