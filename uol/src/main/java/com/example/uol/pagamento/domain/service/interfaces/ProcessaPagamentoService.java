package com.example.uol.pagamento.domain.service.interfaces;

import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;

import java.util.List;
import java.util.Map;

public interface ProcessaPagamentoService {
    List<ItemPagamentoDTO> processarPagamentos(Map<Cobranca, ItemPagamentoDTO> pagamentoCobranca);
}
