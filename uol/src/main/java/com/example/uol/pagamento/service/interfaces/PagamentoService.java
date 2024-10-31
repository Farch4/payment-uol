package com.example.uol.pagamento.service.interfaces;


import com.example.uol.pagamento.controller.dto.PagamentoDTO;
import com.example.uol.pagamento.exception.CobrancaNaoEncontradaException;
import com.example.uol.pagamento.exception.VendedorNaoEncontradoException;

public interface PagamentoService {

    PagamentoDTO processarPagamentos(PagamentoDTO pagamentoRequest) throws VendedorNaoEncontradoException,
            CobrancaNaoEncontradaException;
}
