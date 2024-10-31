package com.example.uol.pagamento.exception;

public class CobrancaNaoEncontradaException extends Exception {

    public CobrancaNaoEncontradaException(String errorMessage) {
        super(errorMessage);
    }
}
