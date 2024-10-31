package com.example.uol.pagamento.domain.model;

import com.example.uol.pagamento.domain.model.enums.StatusPagamento;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Cobranca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    public Cobranca(Long id, BigDecimal valor, StatusPagamento status) {
        this.id = id;
        this.valor = valor;
        this.status = status;
    }

    public Cobranca() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public StatusPagamento getStatus() {
        if(status==null)return StatusPagamento.PENDENTE;
        return status;
    }

    public void receivePayment(BigDecimal valorRecebido){
        this.valor= this.valor.subtract(valorRecebido);
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public void setValor(BigDecimal valor) {
        if(this.valor==null)this.valor = valor;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

