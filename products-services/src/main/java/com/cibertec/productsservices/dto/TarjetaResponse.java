package com.cibertec.productsservices.dto;

import java.math.BigDecimal;

public record TarjetaResponse(
        Long idTarjeta,
        String nomTitular,
        BigDecimal saldoAsignado,
        BigDecimal saldoDisponible
) {
}
