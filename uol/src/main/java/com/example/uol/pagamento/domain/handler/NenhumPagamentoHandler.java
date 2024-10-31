package com.example.uol.pagamento.domain.handler;

import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.model.enums.StatusPagamento;

public final class NenhumPagamentoHandler extends PagamentoHandlerAbs {

    @Override
    public ItemPagamentoDTO handle(Cobranca cobranca, ItemPagamentoDTO pagamentoDTO){
        pagamentoDTO.setStatus(StatusPagamento.PENDENTE.toString());

        return pagamentoDTO;
    }
}
