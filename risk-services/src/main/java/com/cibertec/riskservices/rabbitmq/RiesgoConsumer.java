package com.cibertec.riskservices.rabbitmq;

import com.cibertec.riskservices.entidades.Analisis;
import com.cibertec.riskservices.repositorios.AnalisisRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RiesgoConsumer {

    private final AnalisisRepository analisisRepository;

    public RiesgoConsumer(AnalisisRepository analisisRepository) {
        this.analisisRepository = analisisRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.APELLIDO_QUEUE)
    public void recibirRecarga(RecargaEvent evento) {
        BigDecimal limite = evento.saldoDisponible()
                .multiply(new BigDecimal("0.70"));

        String situacion = evento.montoRecarga().compareTo(limite) <= 0
                ? "Aprobada"
                : "Observada";

        Analisis analisis = Analisis.builder()
                .idRecarga(evento.idRecarga())
                .idTarjeta(evento.idTarjeta())
                .saldoDisponible(evento.saldoDisponible())
                .montoRecarga(evento.montoRecarga())
                .fechaRecarga(evento.fechaRecarga())
                .situacion(situacion)
                .build();

        analisisRepository.save(analisis);
        System.out.println("Riesgo consumió recarga "
                + evento.idRecarga() + ": " + situacion);
    }
}
