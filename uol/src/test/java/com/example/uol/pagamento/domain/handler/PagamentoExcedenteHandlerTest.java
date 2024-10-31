package com.example.uol.pagamento.domain.handler;


import com.example.uol.pagamento.constants.SqsQueueConstants;
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

import static org.mockito.Mockito.*;
        import static org.junit.jupiter.api.Assertions.*;

class PagamentoExcedenteHandlerTest {

    @Mock
    private CobrancaRepository cobrancaRepository;

    @Mock
    private SQSService sqsService;

    @Mock
    private PagamentoTotalHandler pagamentoTotalHandler;

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
        pagamentoDTO.setValor(new BigDecimal("150.00"));

        ItemPagamentoDTO result = pagamentoHandler.handle(cobranca, pagamentoDTO);

        assertEquals(StatusPagamento.EXCEDENTE.toString(), result.getStatus());
        verify(cobrancaRepository).save(cobranca);
        verify(sqsService).sendMessage(result, SqsQueueConstants.PAGAMENTOS_EXCEDENTES_QUEUE);
    }

}
