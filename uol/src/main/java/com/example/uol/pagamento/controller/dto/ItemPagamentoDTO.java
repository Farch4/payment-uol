package com.example.uol.pagamento.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemPagamentoDTO  implements Serializable {

    private static final long serialVersionUID = 2L;

    @JsonProperty("cobrancaId")
    private Long cobrancaId;

    @JsonProperty("valor")
    private BigDecimal valor;

    @JsonProperty("status")
    private String status;

    public Long getCobrancaId() {
        return cobrancaId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public ItemPagamentoDTO() {
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setCobrancaId(Long cobrancaId) {
        this.cobrancaId = cobrancaId;
    }

    public void setValor(BigDecimal valor) {
        if(this.valor==null)this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ItemPagamentoDTO{" +
                "cobrancaId=" + cobrancaId +
                ", valor=" + valor +
                ", status='" + status + '\'' +
                '}';
    }
}
