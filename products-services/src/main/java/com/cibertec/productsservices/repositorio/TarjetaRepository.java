package com.cibertec.productsservices.repositorio;

import com.cibertec.productsservices.entidades.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
}
