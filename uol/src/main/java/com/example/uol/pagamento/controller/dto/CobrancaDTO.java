package com.example.uol.pagamento.controller.dto;

import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.model.enums.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public class CobrancaDTO implements Serializable {

    private static final long serialVersionUID = 3L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("valor")
    private BigDecimal valor;

    @JsonProperty("status")
    private String status;

    public CobrancaDTO(Cobranca cobranca) {
        this.id = cobranca.getId();
        this.valor = cobranca.getValor();
        this.status = cobranca.getStatus().toString();
    }

    public CobrancaDTO() {
    }
}
