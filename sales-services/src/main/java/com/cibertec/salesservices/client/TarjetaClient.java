package com.cibertec.salesservices.client;

import com.cibertec.salesservices.dto.RecargaRequest;
import com.cibertec.salesservices.dto.TarjetaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(
        name = "products-services",
        url = "http://localhost:8081",
        contextId = "tarjetaClient"
)
public interface TarjetaClient {

    @GetMapping("/tarjetas/{id}")
    TarjetaResponse getTarjetaById(@PathVariable Long id);

  @PutMapping("/tarjetas/{id}")
  TarjetaResponse putTarjetaById(@PathVariable Long id, RecargaRequest request);
}
