package com.example.uol.pagamento.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class PagamentoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("vendedorId")
    private Long vendedorId;

    @JsonProperty("pagamentos")
    private List<ItemPagamentoDTO> pagamentos;

    public List<ItemPagamentoDTO> getPagamentos() {
        return pagamentos;
    }

    public Long getVendedorId() {
        return vendedorId;
    }

    public PagamentoDTO() {
    }

    public void setPagamentos(List<ItemPagamentoDTO> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public void setVendedorId(Long vendedorId) {
        this.vendedorId = vendedorId;
    }
}

