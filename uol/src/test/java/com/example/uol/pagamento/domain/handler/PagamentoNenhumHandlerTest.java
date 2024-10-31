package com.example.uol.pagamento.domain.handler;


import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.model.enums.StatusPagamento;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.service.impl.SQSService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class PagamentoNenhumHandlerTest {

    @Mock
    private CobrancaRepository cobrancaRepository;

    @Mock
    private SQSService sqsService;

    @InjectMocks
    private PagamentoHandler pagamentoHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pagamentoHandler = new PagamentoHandler(cobrancaRepository, sqsService);
    }

    @Test
    void testHandle_PagamentoExcedente() {
        Cobranca cobranca = new Cobranca();
        cobranca.setValor(new BigDecimal("100.00"));
        cobranca.setStatus(StatusPagamento.PENDENTE);

        ItemPagamentoDTO pagamentoDTO = new ItemPagamentoDTO();
        pagamentoDTO.setValor(new BigDecimal("0.00"));

        ItemPagamentoDTO result = pagamentoHandler.handle(cobranca, pagamentoDTO);

        assertEquals(StatusPagamento.PENDENTE.toString(), result.getStatus());
        verify(cobrancaRepository, never()).save(cobranca);
        verify(sqsService, never()).sendMessage(any(), anyString());
    }

}
