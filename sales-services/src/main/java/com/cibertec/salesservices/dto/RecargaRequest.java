package com.cibertec.salesservices.dto;

import java.math.BigDecimal;

public record RecargaRequest(
        Long idTarjeta,
        BigDecimal montoRecarga
) {
}
