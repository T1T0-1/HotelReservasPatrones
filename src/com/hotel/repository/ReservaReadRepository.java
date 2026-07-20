package com.hotel.repository;

import com.hotel.domain.Reserva;
import java.util.List;
import java.util.Optional;

public interface ReservaReadRepository {
    List<Reserva> listar();
    Optional<Reserva> buscarPorId(String id);
}
