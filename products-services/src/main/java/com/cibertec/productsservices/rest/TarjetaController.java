package com.cibertec.productsservices.rest;

import com.cibertec.productsservices.dto.TarjetaRequest;
import com.cibertec.productsservices.dto.TarjetaResponse;
import com.cibertec.productsservices.dto.TarjetaUpdateRequest;
import com.cibertec.productsservices.negocio.TarjetaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tarjetas")
public class TarjetaController {

    private final TarjetaService tarjetaService;

    public TarjetaController(TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    @GetMapping
    public List<TarjetaResponse> getAllTarjetas() {
        return tarjetaService.getAllTarjetas();
    }

    @GetMapping("/{id}")
    public TarjetaResponse getTarjetaById(@PathVariable Long id) {
        return tarjetaService.getTarjetaById(id);
    }

    @PostMapping
    public TarjetaResponse createTarjeta(@RequestBody TarjetaRequest request) {
        return tarjetaService.createTarjeta(request);
    }

  @PutMapping("/{id}")
  public TarjetaResponse putTarjetaById(@PathVariable Long id, @RequestBody TarjetaUpdateRequest request) {
    return tarjetaService.putTarjetaById(id, request);
  }
}
