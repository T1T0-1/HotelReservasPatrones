package com.hotel.repository;

import com.hotel.domain.Habitacion;

public interface HabitacionWriteRepository {
    void guardar(Habitacion habitacion);
    void eliminar(String numero);
}
