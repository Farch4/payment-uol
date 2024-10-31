package com.example.uol.pagamento.domain.service.impl;


import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.domain.handler.PagamentoHandlerInicial;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.domain.service.interfaces.ProcessaPagamentoService;
import com.example.uol.pagamento.service.impl.SQSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessaPagamentoServiceImpl implements ProcessaPagamentoService {


    PagamentoHandlerInicial pagamentoHandlerInicial;
    CobrancaRepository cobrancaRepository;
    SQSService sqsService;

    @Autowired
    public ProcessaPagamentoServiceImpl(CobrancaRepository cobrancaRepository, SQSService sqsService) {
        this.cobrancaRepository = cobrancaRepository;
        this.sqsService = sqsService;
    }

    @Override
    public List<ItemPagamentoDTO> processarPagamentos(Map<Cobranca, ItemPagamentoDTO> pagamentoCobranca) {
        PagamentoHandlerInicial pagamentoHandlerInicial = new PagamentoHandlerInicial(cobrancaRepository, sqsService);

        List<ItemPagamentoDTO> itemPagamentoDTOS = pagamentoCobranca.entrySet().parallelStream()
                .map(item -> pagamentoHandlerInicial.handle(item.getKey(), item.getValue()))
                .collect(Collectors.toList());

        return itemPagamentoDTOS;
    }

}
