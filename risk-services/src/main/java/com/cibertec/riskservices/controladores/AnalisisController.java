package com.cibertec.riskservices.controladores;

import com.cibertec.riskservices.entidades.Analisis;
import com.cibertec.riskservices.repositorios.AnalisisRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analisis")
public class AnalisisController {

    private final AnalisisRepository analisisRepository;

    public AnalisisController(AnalisisRepository analisisRepository) {
        this.analisisRepository = analisisRepository;
    }

    @GetMapping
    public List<Analisis> listar() {
        return analisisRepository.findAll();
    }
}
