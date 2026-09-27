package com.cibertec.salesservices.rest;

import com.cibertec.salesservices.dto.RecargaRequest;
import com.cibertec.salesservices.dto.RecargaResponse;
import com.cibertec.salesservices.negocio.RecargaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recargas")
public class RecargaController {

    private final RecargaService recargaService;

    public RecargaController(RecargaService recargaService) {
        this.recargaService = recargaService;
    }

    @PostMapping
    public RecargaResponse createRecarga(@RequestBody RecargaRequest request) {
        return recargaService.createRecarga(request);
    }
}
