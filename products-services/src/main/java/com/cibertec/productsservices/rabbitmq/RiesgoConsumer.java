package com.cibertec.productsservices.rabbitmq;

import com.cibertec.productsservices.entidades.Analisis;
import com.cibertec.productsservices.repositorio.AnalisisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RiesgoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RiesgoConsumer.class);

    private final AnalisisRepository analisisRepository;

    public RiesgoConsumer(AnalisisRepository analisisRepository) {
        this.analisisRepository = analisisRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.APELLIDO_QUEUE)
    public void onRecarga(RecargaEvent event) {
        Analisis analisis = toAnalisis(event);
        analisisRepository.save(analisis);
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