package com.microservice.pagamento.service;

import com.microservice.pagamento.model.Pagamento;
import com.microservice.pagamento.model.StatusPagamento;
import com.microservice.pagamento.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PagamentoService {
    
    @Autowired
    private final PagamentoRepository pagamentoRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    public void processar(Pagamento pagamento) {
        try {
            // Validar e processar pagamento
            validarPagamento(pagamento);
            
            // Configurar dados do pagamento
            pagamento.setStatus(StatusPagamento.APROVADO);
            pagamento.setDataPagamento(LocalDateTime.now());
            
            // Salvar pagamento
            pagamentoRepository.save(pagamento);
            
            // Notificar serviço de pedidos
            notificarAtualizacaoPedido(pagamento);
            
            log.info("Pagamento processado com sucesso para o pedido: {}", pagamento.getPedidoId());
            
        } catch (Exception e) {
            log.error("Erro ao processar pagamento para o pedido: {}", pagamento.getPedidoId(), e);
            throw new RuntimeException("Erro ao processar pagamento", e);
        }
    }

    private void validarPagamento(Pagamento pagamento) {
        if (pagamento.getValorPago().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser maior que zero");
        }
        
        if (pagamento.getPedidoId() == null) {
            throw new IllegalArgumentException("ID do pedido não pode ser nulo");
        }

        if (pagamento.getFormaPagamento() == null || pagamento.getFormaPagamento().trim().isEmpty()) {
            throw new IllegalArgumentException("Forma de pagamento não pode ser vazia");
        }
    }

    private void notificarAtualizacaoPedido(Pagamento pagamento) {
        String mensagem = String.format("%d,%s,%s", 
            pagamento.getPedidoId(), 
            pagamento.getStatus(),
            pagamento.getFormaPagamento());
            
        jmsTemplate.convertAndSend("fila.atualizacao-pedidos", mensagem);
        log.info("Notificação de atualização enviada para o pedido: {}", pagamento.getPedidoId());
    }
}
