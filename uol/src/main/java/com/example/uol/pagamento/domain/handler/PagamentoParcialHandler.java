package com.example.uol.pagamento.domain.handler;


import com.example.uol.pagamento.constants.SqsQueueConstants;
import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.model.enums.StatusPagamento;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.service.impl.SQSService;

import java.math.BigDecimal;

public final class PagamentoParcialHandler extends PagamentoHandlerAbs {

    PagamentoParcialHandler(CobrancaRepository cobrancaRepository,
                            SQSService sqsService) {
        super(new NenhumPagamentoHandler(), cobrancaRepository, sqsService);
    }

    @Override
    public ItemPagamentoDTO handle(Cobranca cobranca, ItemPagamentoDTO pagamentoDTO){
        if(pagamentoDTO.getValor().compareTo(cobranca.getValor()) < 0 &&
        pagamentoDTO.getValor().compareTo(BigDecimal.valueOf(0)) > 0){
            changeAndSaveCobranca(StatusPagamento.PARCIAL, cobranca, pagamentoDTO.getValor());

            pagamentoDTO.setStatus(StatusPagamento.PARCIAL.toString());

            sqsService.sendMessage(pagamentoDTO, SqsQueueConstants.PAGAMENTOS_PARCIAIS_QUEUE);

            return pagamentoDTO;
        }
        return nextHandler.handle(cobranca, pagamentoDTO);
    }
}
