package com.cibertec.productsservices.dto;

import java.math.BigDecimal;

public record TarjetaUpdateRequest(
    Long idTarjeta,
    BigDecimal montoRecarga
) {
}
