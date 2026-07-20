package com.hotel.repository;

import com.hotel.domain.Reserva;

public interface ReservaWriteRepository {
    void guardar(Reserva reserva);
    void eliminar(String id);
}
