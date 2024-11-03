package com.microservice.pagamento.model.dto;

import com.microservice.pagamento.model.FormaPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO implements Serializable {
    private Long id;
    private BigDecimal valorTotal;
    private FormaPagamento formaPagamento;
}
