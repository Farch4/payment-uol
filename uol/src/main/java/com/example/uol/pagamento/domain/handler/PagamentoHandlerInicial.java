package com.example.uol.pagamento.domain.handler;

import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.model.enums.StatusPagamento;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.service.impl.SQSService;


public final class PagamentoHandlerInicial extends PagamentoHandler{

    public PagamentoHandlerInicial(CobrancaRepository cobrancaRepository,
                            SQSService sqsService) {
        super(new PagamentoTotalHandler(cobrancaRepository, sqsService),
                cobrancaRepository, sqsService);
    }

    @Override
    public ItemPagamentoDTO handle(Cobranca cobranca, ItemPagamentoDTO pagamentoDTO){

        if(pagamentoDTO.getValor().compareTo(cobranca.getValor()) > 0){

            changeAndSaveCobranca(StatusPagamento.EXCEDENTE, cobranca, pagamentoDTO.getValor());

            pagamentoDTO.setStatus(cobranca.getStatus().toString());
            sqsService.sendMessageToExdentQueue(pagamentoDTO);

            return pagamentoDTO;
        }

        return nextHandler.handle(cobranca, pagamentoDTO);

    }
}