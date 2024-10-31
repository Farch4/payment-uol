package com.example.uol.pagamento.domain.handler;

import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.model.enums.StatusPagamento;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.service.impl.SQSService;

import java.math.BigDecimal;

abstract sealed class PagamentoHandlerAbs permits PagamentoTotalHandler,
        PagamentoParcialHandler, PagamentoHandler, NenhumPagamentoHandler{

    protected PagamentoHandlerAbs nextHandler;
    protected SQSService sqsService;
    protected CobrancaRepository cobrancaRepository;

    PagamentoHandlerAbs(PagamentoHandlerAbs nextHandler,
                        CobrancaRepository cobrancaRepository,
                        SQSService sqsService) {
        this.nextHandler=nextHandler;
        this.sqsService = sqsService;
        this.cobrancaRepository = cobrancaRepository;
    }

    PagamentoHandlerAbs() {
    }

    public abstract ItemPagamentoDTO handle(Cobranca cobranca, ItemPagamentoDTO pagamentoDTO);

    protected void changeAndSaveCobranca(StatusPagamento status, Cobranca cobranca, BigDecimal value){
        cobranca.receivePayment(value);
        cobranca.setStatus(status);
        this.cobrancaRepository.save(cobranca);
    }
}
