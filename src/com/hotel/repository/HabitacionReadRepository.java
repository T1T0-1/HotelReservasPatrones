package com.hotel.repository;

import com.hotel.domain.Habitacion;
import java.util.List;
import java.util.Optional;

public interface HabitacionReadRepository {
    List<Habitacion> listar();
    Optional<Habitacion> buscarPorNumero(String numero);
}
