package com.example.uol.pagamento.service.impl;


import com.example.uol.pagamento.controller.dto.CobrancaDTO;
import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.controller.dto.PagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.domain.repository.VendedorRepository;
import com.example.uol.pagamento.domain.service.interfaces.ProcessaPagamentoService;
import com.example.uol.pagamento.exception.CobrancaNaoEncontradaException;
import com.example.uol.pagamento.exception.VendedorNaoEncontradoException;
import com.example.uol.pagamento.service.interfaces.CobrancaService;
import com.example.uol.pagamento.service.interfaces.PagamentoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CobrancaServiceImpl implements CobrancaService {

    private final CobrancaRepository cobrancaRepository;

    public CobrancaServiceImpl(CobrancaRepository cobrancaRepository
    ) {
        this.cobrancaRepository = cobrancaRepository;
    }


    @Override
    public List<CobrancaDTO> getAll() {
       return cobrancaRepository.findAll().stream().map(cobranca -> new CobrancaDTO(cobranca)).toList();
    }
}
