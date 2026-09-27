package com.cibertec.productsservices.negocio;

import com.cibertec.productsservices.entidades.Analisis;
import com.cibertec.productsservices.repositorio.AnalisisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalisisService {

    private final AnalisisRepository analisisRepository;

    public AnalisisService(AnalisisRepository analisisRepository) {
        this.analisisRepository = analisisRepository;
    }

    public List<Analisis> getAllAnalisis() {
        return analisisRepository.findAll();
    }

    public Analisis saveAnalisis(Analisis analisis) {
        return analisisRepository.save(analisis);
    }
}
