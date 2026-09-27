package com.cibertec.productsservices.rabbitmq;

import com.cibertec.productsservices.entidades.Analisis;
import com.cibertec.productsservices.repositorio.AnalisisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiesgoConsumerTest {

    @Mock
    private AnalisisRepository analisisRepository;

    @InjectMocks
    private RiesgoConsumer riesgoConsumer;

    @Test
    void onRecargaMarksObservadaWhenAmountExceeds70Percent() {
        RecargaEvent event = new RecargaEvent(1L, 10L, new BigDecimal("100.00"), new BigDecimal("80.00"), LocalDateTime.now());
        when(analisisRepository.save(any(Analisis.class))).thenAnswer(invocation -> invocation.getArgument(0));

        riesgoConsumer.onRecarga(event);

        verify(analisisRepository).save(any(Analisis.class));
    }

    @Test
    void onRecargaMarksAprobadaWhenAmountDoesNotExceed70Percent() {
        RecargaEvent event = new RecargaEvent(2L, 20L, new BigDecimal("200.00"), new BigDecimal("100.00"), LocalDateTime.now());
        when(analisisRepository.save(any(Analisis.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Analisis analisis = riesgoConsumer.toAnalisis(event);

        assertEquals("Aprobada", analisis.getSituacion());
    }
}
