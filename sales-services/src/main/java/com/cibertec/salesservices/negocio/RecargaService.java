package com.cibertec.salesservices.negocio;

import com.cibertec.salesservices.client.TarjetaClient;
import com.cibertec.salesservices.dto.RecargaRequest;
import com.cibertec.salesservices.dto.RecargaResponse;
import com.cibertec.salesservices.dto.TarjetaResponse;
import com.cibertec.salesservices.entidades.Recarga;
import com.cibertec.salesservices.rabbitmq.RecargaEvent;
import com.cibertec.salesservices.rabbitmq.RecargaProducer;
import com.cibertec.salesservices.repositorio.RecargaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class RecargaService {

    private final RecargaRepository recargaRepository;
    private final TarjetaClient tarjetaClient;
    private final RecargaProducer recargaProducer;

    public RecargaService(RecargaRepository recargaRepository, TarjetaClient tarjetaClient, RecargaProducer recargaProducer) {
        this.recargaRepository = recargaRepository;
        this.tarjetaClient = tarjetaClient;
        this.recargaProducer = recargaProducer;
    }

    public RecargaResponse createRecarga(RecargaRequest request) {
      if (request.montoRecarga() == null
          || request.montoRecarga().compareTo(BigDecimal.ZERO) <= 0) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El monto de recarga debe ser mayor a cero");
      }
        TarjetaResponse tarjeta;
        try {
            tarjeta = tarjetaClient.getTarjetaById(request.idTarjeta());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La tarjeta no existe");
        }

        Recarga recarga = Recarga.builder()
                .idTarjeta(tarjeta.idTarjeta())
                .saldoDisponible(tarjeta.saldoDisponible()
                .montoRecarga(request.montoRecarga())
                .fechaRecarga(LocalDateTime.now())
                .build();

        Recarga saved = recargaRepository.save(recarga);

        recargaProducer.publish(new RecargaEvent(
                saved.getIdRecarga(),
                saved.getIdTarjeta(),
                saved.getSaldoDisponible(),
                saved.getMontoRecarga(),
                saved.getFechaRecarga()
        ));

        return new RecargaResponse(
                saved.getIdRecarga(),
                saved.getIdTarjeta(),
                saved.getSaldoDisponible(),
                saved.getMontoRecarga(),
                saved.getFechaRecarga()
        );
    }
}
