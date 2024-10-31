package com.example.uol.pagamento.service;



import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.controller.dto.PagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.model.Vendedor;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.domain.repository.VendedorRepository;
import com.example.uol.pagamento.domain.service.interfaces.ProcessaPagamentoService;
import com.example.uol.pagamento.exception.CobrancaNaoEncontradaException;
import com.example.uol.pagamento.exception.VendedorNaoEncontradoException;
import com.example.uol.pagamento.service.impl.PagamentoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PagamentoServiceTest {

    @Mock
    private VendedorRepository vendedorRepository;

    @Mock
    private CobrancaRepository cobrancaRepository;

    @Mock
    private ProcessaPagamentoService processaPagamentoService;

    @InjectMocks
    private PagamentoServiceImpl pagamentoService;

    private PagamentoDTO pagamentoDTO;

    @BeforeEach
    public void setUp() {
        pagamentoDTO = new PagamentoDTO();
        pagamentoDTO.setVendedorId(1L);
        ItemPagamentoDTO item1 = new ItemPagamentoDTO();
        item1.setCobrancaId(1L);
        item1.setValor(BigDecimal.valueOf(1500.00));
        pagamentoDTO.setPagamentos(Collections.singletonList(item1));
    }

    @Test
    public void testProcessarPagamentos_Success() throws Exception {

        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(new Vendedor()));

        Cobranca cobranca = new Cobranca();
        when(cobrancaRepository.findById(1L)).thenReturn(Optional.of(cobranca));

        when(processaPagamentoService.processarPagamentos(any())).thenReturn(Collections.singletonList(pagamentoDTO.getPagamentos().get(0)));

        PagamentoDTO resultado = pagamentoService.processarPagamentos(pagamentoDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.getPagamentos().size());
        assertEquals(resultado.getPagamentos().get(0).getValor().compareTo(BigDecimal.valueOf(1500.00)), 0);
    }

    @Test
    public void testProcessarPagamentos_VendedorNaoEncontrado() {

        when(vendedorRepository.findById(1L)).thenReturn(Optional.empty());

        VendedorNaoEncontradoException exception = assertThrows(VendedorNaoEncontradoException.class, () -> {
            pagamentoService.processarPagamentos(pagamentoDTO);
        });

        assertEquals("Vendedor não encontrado", exception.getMessage());
    }

    @Test
    public void testProcessarPagamentos_CobrancaNaoEncontrada() {

        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(new Vendedor()));

        when(cobrancaRepository.findById(1L)).thenReturn(Optional.empty());

        CobrancaNaoEncontradaException exception = assertThrows(CobrancaNaoEncontradaException.class, () -> {
            pagamentoService.processarPagamentos(pagamentoDTO);
        });

        assertEquals("Cobrança não encontrada", exception.getMessage());
    }
}
