package com.example.uol.pagamento.controller;

import com.example.uol.pagamento.controller.dto.PagamentoDTO;
import com.example.uol.pagamento.exception.CobrancaNaoEncontradaException;
import com.example.uol.pagamento.exception.VendedorNaoEncontradoException;
import com.example.uol.pagamento.service.interfaces.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    public ResponseEntity<?> efetuaPagamentos(@RequestBody PagamentoDTO pagamentoDTO) {
        try {
            PagamentoDTO pagamentoResponse = pagamentoService.processarPagamentos(pagamentoDTO);
            return ResponseEntity.ok(pagamentoResponse);
        } catch (VendedorNaoEncontradoException | CobrancaNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
