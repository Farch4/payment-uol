package com.example.uol.pagamento.service.impl;


import com.example.uol.pagamento.controller.dto.ItemPagamentoDTO;
import com.example.uol.pagamento.controller.dto.PagamentoDTO;
import com.example.uol.pagamento.domain.model.Cobranca;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.domain.repository.VendedorRepository;
import com.example.uol.pagamento.domain.service.interfaces.ProcessaPagamentoService;
import com.example.uol.pagamento.exception.CobrancaNaoEncontradaException;
import com.example.uol.pagamento.exception.VendedorNaoEncontradoException;
import com.example.uol.pagamento.service.interfaces.PagamentoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PagamentoServiceImpl implements PagamentoService {

    private final VendedorRepository vendedorRepository;
    private final CobrancaRepository cobrancaRepository;
    private final ProcessaPagamentoService processaPagamentoService;

    public PagamentoServiceImpl(VendedorRepository vendedorRepository,
                                CobrancaRepository cobrancaRepository,
                                ProcessaPagamentoService processaPagamentoService
    ) {
        this.vendedorRepository = vendedorRepository;
        this.cobrancaRepository = cobrancaRepository;
        this.processaPagamentoService = processaPagamentoService;
    }

    private void validateVendedorExitence(PagamentoDTO pagamentoDTO) throws VendedorNaoEncontradoException{
        vendedorRepository.findById(pagamentoDTO.getVendedorId())
                .orElseThrow(() -> new VendedorNaoEncontradoException("Vendedor não encontrado"));
    }

    private Map<Cobranca, ItemPagamentoDTO> getPagamentoCobranca(PagamentoDTO pagamentoDTO)
            throws CobrancaNaoEncontradaException{
        Map<Cobranca, ItemPagamentoDTO> pagamentoCobranca = new HashMap<>();

        for(ItemPagamentoDTO pagamento: pagamentoDTO.getPagamentos()){
            pagamentoCobranca.put(cobrancaRepository.findById(pagamento.getCobrancaId())
                    .orElseThrow(() ->
                            new CobrancaNaoEncontradaException("Cobrança não encontrada")),
                    pagamento);
        }
        return pagamentoCobranca;
    }


    @Override
    public PagamentoDTO processarPagamentos(PagamentoDTO pagamentoDTO)
            throws CobrancaNaoEncontradaException, VendedorNaoEncontradoException {

        validateVendedorExitence(pagamentoDTO);

        pagamentoDTO.setPagamentos(processaPagamentoService.
                processarPagamentos(getPagamentoCobranca(pagamentoDTO)));
        return pagamentoDTO;
    }
}
