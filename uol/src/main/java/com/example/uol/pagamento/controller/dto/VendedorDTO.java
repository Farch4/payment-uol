package com.example.uol.pagamento.controller.dto;

import com.example.uol.pagamento.domain.model.Vendedor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class VendedorDTO implements Serializable {

    private static final long serialVersionUID = 4L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    public VendedorDTO(Vendedor vendedor) {
        this.id = vendedor.getId();
        this.nome = vendedor.getNome();
    }

    public VendedorDTO() {
    }
}
