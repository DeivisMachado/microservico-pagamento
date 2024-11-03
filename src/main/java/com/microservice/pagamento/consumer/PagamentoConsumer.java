package com.microservice.pagamento.consumer;

import com.microservice.pagamento.model.Pagamento;
import com.microservice.pagamento.service.PagamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class PagamentoConsumer {

    private final PagamentoService pagamentoService;

   @JmsListener(destination = "fila.pedidos")
    public void receberPedido(String mensagem) {
        try {
            log.info("Mensagem recebida: {}", mensagem);
            
            // Formato da mensagem: "pedidoId,valorTotal,formaPagamento"
            String[] dados = mensagem.split(",");
            Long pedidoId = Long.parseLong(dados[0]);
            BigDecimal valor = new BigDecimal(dados[1]);
            String formaPagamento = "CARTAO_CREDITO";

            Pagamento pagamento = new Pagamento();
            pagamento.setPedidoId(pedidoId);
            pagamento.setValorPago(valor);
            pagamento.setFormaPagamento(formaPagamento);
            
            pagamentoService.processar(pagamento);
            
        } catch (Exception e) {
            log.error("Erro ao processar mensagem: {}", mensagem, e);
        }
    }
}