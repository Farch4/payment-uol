package com.example.uol.pagamento.domain.repository;

import com.example.uol.pagamento.domain.model.Cobranca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, Long> {
}
