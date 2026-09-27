package com.cibertec.salesservices.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RecargaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecargaProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public RecargaProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(RecargaEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.RIESGO_EXCHANGE,
                RabbitMQConfig.APELLIDO_ROUTING_KEY,
                event
        );
        LOGGER.info("Evento de recarga publicado en cola {}: {}", RabbitMQConfig.APELLIDO_QUEUE, event);
    }
}
