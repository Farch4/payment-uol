package com.example.uol.pagamento.service.interfaces;


import com.example.uol.pagamento.controller.dto.CobrancaDTO;
import com.example.uol.pagamento.controller.dto.VendedorDTO;

import java.util.List;

public interface VendedorService {

    List<VendedorDTO> getAll();
}
