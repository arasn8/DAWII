package com.cibertec.salesservices.dto;

import java.math.BigDecimal;

public record TarjetaResponse(
        Long idTarjeta,
        String nomTitular,
        BigDecimal saldoAsignado,
        BigDecimal saldoDisponible
) {
}
