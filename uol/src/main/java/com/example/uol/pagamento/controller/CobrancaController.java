package com.example.uol.pagamento.controller;

import com.example.uol.pagamento.controller.dto.PagamentoDTO;
import com.example.uol.pagamento.exception.CobrancaNaoEncontradaException;
import com.example.uol.pagamento.exception.VendedorNaoEncontradoException;
import com.example.uol.pagamento.service.interfaces.CobrancaService;
import com.example.uol.pagamento.service.interfaces.PagamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cobrancas")
public class CobrancaController {

    private final CobrancaService cobrancaService;

    public CobrancaController(CobrancaService cobrancaService) {
        this.cobrancaService = cobrancaService;
    }

    @GetMapping
    public ResponseEntity<?> consultaCobrancas() {
        return ResponseEntity.ok(cobrancaService.getAll());
    }
}
