package com.example.uol.pagamento.domain.model;

import jakarta.persistence.*;

@Entity
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public Vendedor(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Vendedor(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
}
