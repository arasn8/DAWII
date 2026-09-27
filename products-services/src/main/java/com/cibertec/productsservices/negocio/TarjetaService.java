package com.cibertec.productsservices.negocio;

import com.cibertec.productsservices.dto.TarjetaRequest;
import com.cibertec.productsservices.dto.TarjetaResponse;
import com.cibertec.productsservices.dto.TarjetaUpdateRequest;
import com.cibertec.productsservices.entidades.Tarjeta;
import com.cibertec.productsservices.repositorio.TarjetaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TarjetaService {

    private final TarjetaRepository tarjetaRepository;

    public TarjetaService(TarjetaRepository tarjetaRepository) {
        this.tarjetaRepository = tarjetaRepository;
    }

    public List<TarjetaResponse> getAllTarjetas() {
        return tarjetaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public TarjetaResponse getTarjetaById(Long id) {
        Tarjeta tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarjeta no encontrada"));
        return toResponse(tarjeta);
    }

    public TarjetaResponse createTarjeta(TarjetaRequest request) {
        Tarjeta tarjeta = Tarjeta.builder()
                .idTarjeta(request.idTarjeta())
                .nomTitular(request.nomTitular())
                .saldoAsignado(request.saldoAsignado())
                .saldoDisponible(request.saldoDisponible())
                .build();

        return toResponse(tarjetaRepository.save(tarjeta));
    }

    private TarjetaResponse toResponse(Tarjeta tarjeta) {
        return new TarjetaResponse(
                tarjeta.getIdTarjeta(),
                tarjeta.getNomTitular(),
                tarjeta.getSaldoAsignado(),
                tarjeta.getSaldoDisponible()
        );
    }

  public TarjetaResponse putTarjetaById(Long id, TarjetaUpdateRequest request) {
    Tarjeta tarjeta = tarjetaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarjeta no encontrada"));

    tarjeta.setSaldoAsignado(request.montoRecarga().add(tarjeta.getSaldoAsignado()));
    return toResponse(tarjetaRepository.save(tarjeta));
  }
}
