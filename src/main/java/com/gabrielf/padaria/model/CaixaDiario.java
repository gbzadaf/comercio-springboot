package com.gabrielf.padaria.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "caixa_diario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaixaDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private LocalDate data;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalVendas;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalDespesas;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany(mappedBy = "caixaDiario")
    private List<Venda> vendas;

    public enum Status {
        ABERTO,
        FECHADO
    }

}
