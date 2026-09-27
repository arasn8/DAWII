package com.cibertec.productsservices.rest;

import com.cibertec.productsservices.entidades.Analisis;
import com.cibertec.productsservices.negocio.AnalisisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analisis")
public class AnalisisController {

    private final AnalisisService analisisService;

    public AnalisisController(AnalisisService analisisService) {
        this.analisisService = analisisService;
    }

    @GetMapping
    public List<Analisis> getAllAnalisis() {
        return analisisService.getAllAnalisis();
    }
}
