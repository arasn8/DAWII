package com.cibertec.productsservices.rabbitmq;

import com.cibertec.productsservices.dto.TarjetaUpdateRequest;
import com.cibertec.productsservices.entidades.Analisis;
import com.cibertec.productsservices.negocio.TarjetaService;
import com.cibertec.productsservices.repositorio.AnalisisRepository;
import com.cibertec.productsservices.repositorio.TarjetaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RiesgoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RiesgoConsumer.class);

    private final AnalisisRepository analisisRepository;
    private final TarjetaService tarjetaService;

    public RiesgoConsumer(AnalisisRepository analisisRepository, TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
        this.analisisRepository = analisisRepository;
    }

 
    public void onRecarga(RecargaEvent event) {
        Analisis analisis = toAnalisis(event);
        analisisRepository.save(analisis);
        tarjetaService.putTarjetaById(analisis.getIdTarjeta(), new TarjetaUpdateRequest(analisis.getIdTarjeta(), analisis.getMontoRecarga()));
        LOGGER.info("Recarga evaluada por riesgo: {}", analisis.getSituacion());
    }

    public Analisis toAnalisis(RecargaEvent event) {
        BigDecimal setentaPorciento = event.saldoDisponible().multiply(new BigDecimal("0.70"));
        String situacion = event.montoRecarga().compareTo(setentaPorciento) > 0 ? "Observada" : "Aprobada";

        return Analisis.builder()
                .idRecarga(event.idRecarga())
                .idTarjeta(event.idTarjeta())
                .saldoDisponible(event.saldoDisponible())
                .montoRecarga(event.montoRecarga())
                .fechaRecarga(event.fechaRecarga())
                .situacion(situacion)
                .build();
    }
}
