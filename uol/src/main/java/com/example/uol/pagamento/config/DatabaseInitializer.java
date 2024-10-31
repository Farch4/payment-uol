package com.example.uol.pagamento.config;

import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.model.Vendedor;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.domain.repository.VendedorRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DatabaseInitializer {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private CobrancaRepository cobrancaRepository;

    @PostConstruct
    public void init() {
        Vendedor vendedor = new Vendedor();
        vendedor.setNome("Saul Goodman");
        vendedorRepository.save(vendedor);

        vendedor = new Vendedor();
        vendedor.setNome("Walter White");
        vendedorRepository.save(vendedor);

        vendedor = new Vendedor();
        vendedor.setNome("Jesse Pinkman");
        vendedorRepository.save(vendedor);

        Cobranca cobranca = new Cobranca();
        cobranca.setValor(BigDecimal.valueOf(100000.0));
        cobrancaRepository.save(cobranca);

        cobranca = new Cobranca();
        cobranca.setValor(BigDecimal.valueOf(50000.0));
        cobrancaRepository.save(cobranca);

        cobranca = new Cobranca();
        cobranca.setValor(BigDecimal.valueOf(300000.0));
        cobrancaRepository.save(cobranca);

    }
}
