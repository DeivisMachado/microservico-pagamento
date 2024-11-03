package com.microservice.pagamento.repository;

import com.microservice.pagamento.model.Pagamento;
import com.microservice.pagamento.model.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    
    Optional<Pagamento> findByPedidoId(Long pedidoId);
    
    List<Pagamento> findByStatus(StatusPagamento status);
    
    boolean existsByPedidoId(Long pedidoId);
}
