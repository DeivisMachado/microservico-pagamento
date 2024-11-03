package com.microservice.pagamento.model;

import java.math.BigDecimal;
<<<<<<< HEAD
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
=======

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
>>>>>>> f31beeb7fd84831ad96247f917a0da568559b9b7
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
<<<<<<< HEAD
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "pagamentos")
@NoArgsConstructor
@AllArgsConstructor
=======

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
>>>>>>> f31beeb7fd84831ad96247f917a0da568559b9b7
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
<<<<<<< HEAD
    @Column(name = "pedido_id")
    private Long pedidoId;
    
    @Column(name = "forma_pagamento")
    private String formaPagamento;
    
    @Column(name = "valor_pago")
    private BigDecimal valorPago;
    
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
    
    private LocalDateTime dataPagamento;
=======
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    
    @Column(nullable = false)
    private BigDecimal valorPago;
    
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;
    
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
>>>>>>> f31beeb7fd84831ad96247f917a0da568559b9b7
}
