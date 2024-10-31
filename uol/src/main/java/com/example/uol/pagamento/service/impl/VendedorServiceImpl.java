package com.example.uol.pagamento.service.impl;


import com.example.uol.pagamento.controller.dto.CobrancaDTO;
import com.example.uol.pagamento.controller.dto.VendedorDTO;
import com.example.uol.pagamento.domain.repository.CobrancaRepository;
import com.example.uol.pagamento.domain.repository.VendedorRepository;
import com.example.uol.pagamento.service.interfaces.CobrancaService;
import com.example.uol.pagamento.service.interfaces.VendedorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorServiceImpl implements VendedorService {

    private final VendedorRepository vendedorRepository;

    public VendedorServiceImpl(VendedorRepository vendedorRepository
    ) {
        this.vendedorRepository = vendedorRepository;
    }


    @Override
    public List<VendedorDTO> getAll() {
       return vendedorRepository.findAll().stream().map(vendedor -> new VendedorDTO(vendedor)).toList();
    }
}
