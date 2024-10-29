package com.microservice.pagamento.service;

import com.microservice.pagamento.model.Pagamento;
import com.microservice.pagamento.model.StatusPagamento;
import com.microservice.pagamento.repository.PagamentoRepository;
import com.microservice.pagamento.model.dto.PedidoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @JmsListener(destination = "fila.pedidos")
    public void processarPagamento(PedidoDTO pedido) {
        try {
            log.info("Recebido pedido para processamento: {}", pedido);
            
            Pagamento pagamento = Pagamento.builder()
                .pedidoId(pedido.getId())
                .formaPagamento(pedido.getFormaPagamento())
                .valorPago(pedido.getValorTotal())
                .status(StatusPagamento.PROCESSANDO)
                .build();
                
            pagamento = pagamentoRepository.save(pagamento);
            
            // Simula processamento do pagamento
            processarPagamentoExterno(pagamento);
            
            // Atualiza status para PAGO
            pagamento.setStatus(StatusPagamento.PAGO);
            pagamentoRepository.save(pagamento);
            
            // Envia confirmação para o microserviço de pedidos
            jmsTemplate.convertAndSend("fila.pagamentos.confirmados", pagamento);
            
        } catch (Exception e) {
            log.error("Erro ao processar pagamento: ", e);
        }
    }
    
    private void processarPagamentoExterno(Pagamento pagamento) {
        // Simulação de processamento externo
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
