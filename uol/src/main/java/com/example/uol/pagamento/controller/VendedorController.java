package com.example.uol.pagamento.controller;

import com.example.uol.pagamento.service.interfaces.CobrancaService;
import com.example.uol.pagamento.service.interfaces.VendedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {

    private final VendedorService vendedorService;

    public VendedorController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @GetMapping
    public ResponseEntity<?> consultaVendedor() {
        return ResponseEntity.ok(vendedorService.getAll());
    }
}
