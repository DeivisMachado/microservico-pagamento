package com.microservice.pagamento.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.microservice.pagamento.service.PagamentoService;
import com.microservice.pagamento.model.Pagamento;
import com.microservice.pagamento.model.FormaPagamento;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class PedidoConsumer {

    private final PagamentoService pagamentoService;

    @JmsListener(destination = "fila.pedidos")
    public void receberPedido(String mensagem) {
        try {
            String[] dados = mensagem.split(",");
            Long pedidoId = Long.parseLong(dados[0]);
            BigDecimal valorTotal = new BigDecimal(dados[1]);
            FormaPagamento formaPagamento = FormaPagamento.valueOf(dados[2]);

            Pagamento pagamento = new Pagamento();
            pagamento.setPedidoId(pedidoId);
            pagamento.setValorPago(valorTotal);
            pagamento.setFormaPagamento(formaPagamento.toString());
            
            pagamentoService.processar(pagamento);
            
            log.info("Pagamento recebido - PedidoId: {}, Valor: {}, Forma: {}", 
                pedidoId, valorTotal, formaPagamento);
            
        } catch (Exception e) {
            log.error("Erro ao processar mensagem de pagamento: {}", mensagem, e);
        }
    }
}