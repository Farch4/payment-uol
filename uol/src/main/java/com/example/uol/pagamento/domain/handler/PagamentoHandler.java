package com.example.uol.pagamento.domain.handler;

import com.example.uol.pagamento.constants.SqsQueueConstants;
import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.model.enums.StatusPagamento;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.service.impl.SQSService;


public final class PagamentoHandler extends PagamentoHandlerAbs {

    public PagamentoHandler(CobrancaRepository cobrancaRepository,
                            SQSService sqsService) {
        super(new PagamentoTotalHandler(cobrancaRepository, sqsService),
                cobrancaRepository, sqsService);
    }

    @Override
    public ItemPagamentoDTO handle(Cobranca cobranca, ItemPagamentoDTO pagamentoDTO){

        if(pagamentoDTO.getValor().compareTo(cobranca.getValor()) > 0){

            changeAndSaveCobranca(StatusPagamento.EXCEDENTE, cobranca, pagamentoDTO.getValor());

            pagamentoDTO.setStatus(cobranca.getStatus().toString());
            sqsService.sendMessage(pagamentoDTO, SqsQueueConstants.PAGAMENTOS_EXCEDENTES_QUEUE);

            return pagamentoDTO;
        }

        return nextHandler.handle(cobranca, pagamentoDTO);

    }
}
