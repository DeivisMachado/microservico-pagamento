package com.microservice.pagamento.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "pagamentos")
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "pedido_id")
    private Long pedidoId;
    
    @Column(name = "forma_pagamento")
    private String formaPagamento;
    
    @Column(name = "valor_pago")
    private BigDecimal valorPago;
    
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
    
    private LocalDateTime dataPagamento;
}
