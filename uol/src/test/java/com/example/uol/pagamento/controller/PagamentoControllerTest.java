package com.example.uol.pagamento.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;

import com.example.uol.pagamento.controller.dto.PagamentoDTO;
import com.example.uol.pagamento.exception.CobrancaNaoEncontradaException;
import com.example.uol.pagamento.exception.VendedorNaoEncontradoException;
import com.example.uol.pagamento.service.interfaces.PagamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoController pagamentoController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();
    }

    @Test
    public void testEfetuaPagamentos_Success() throws Exception {

        String jsonString = "{ \"vendedorId\": 1, " +
                "\"pagamentos\": [" +
                "{" +
                "\"cobrancaId\": 1, " +
                "\"valor\": 1500000000.75" +
                "}," +
                "{" +
                "\"cobrancaId\": 2, " +
                "\"valor\": 75.50" +
                "}" +
                "] " +
                "}".trim();

        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void testEfetuaPagamentos_VendedorNaoEncontrado() throws Exception {

        when(pagamentoService.processarPagamentos(any(PagamentoDTO.class)))
                .thenThrow(new VendedorNaoEncontradoException("Vendedor não encontrado"));

        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vendedor não encontrado"));
    }

    @Test
    public void testEfetuaPagamentos_CobrancaNaoEncontrada() throws Exception {

        when(pagamentoService.processarPagamentos(any(PagamentoDTO.class)))
                .thenThrow(new CobrancaNaoEncontradaException("Cobrança não encontrada"));

        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Cobrança não encontrada"));
    }

    @Test
    public void testEfetuaPagamentos_InternalServerError() throws Exception {

        when(pagamentoService.processarPagamentos(any(PagamentoDTO.class)))
                .thenThrow(new RuntimeException("Erro interno do servidor"));


        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro interno do servidor"));
    }
}
