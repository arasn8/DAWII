package com.cibertec.riskservices.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String APELLIDO_QUEUE = "MARTINEZ_Queue";

    @Bean
    public DirectExchange riesgoExchange() {
        return new DirectExchange("risk-exchange");
    }

    @Bean
    public Queue apellidoQueue() {
        return new Queue(APELLIDO_QUEUE);
    }

    @Bean
    public Binding apellidoBinding(Queue apellidoQueue, DirectExchange riesgoExchange) {
        return BindingBuilder.bind(apellidoQueue)
                .to(riesgoExchange)
                .with("apellido.risk");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
