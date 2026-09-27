package com.cibertec.productsservices.dto;

import java.math.BigDecimal;

public record TarjetaRequest(
        Long idTarjeta,
        String nomTitular,
        BigDecimal saldoAsignado,
        BigDecimal saldoDisponible
) {
}
