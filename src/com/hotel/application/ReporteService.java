package com.hotel.application;

import com.hotel.domain.EstadoHabitacion;
import com.hotel.domain.EstadoReserva;
import com.hotel.repository.HabitacionReadRepository;
import com.hotel.repository.ReservaReadRepository;
import java.math.BigDecimal;

public final class ReporteService {
    private final HabitacionReadRepository habitaciones;
    private final ReservaReadRepository reservas;

    public ReporteService(HabitacionReadRepository habitaciones, ReservaReadRepository reservas) {
        this.habitaciones = habitaciones; this.reservas = reservas;
    }

    public String generarResumen() {
        var listaHabitaciones = habitaciones.listar();
        var listaReservas = reservas.listar();
        long disponibles = listaHabitaciones.stream().filter(h -> h.getEstado() == EstadoHabitacion.DISPONIBLE).count();
        long reservadas = listaHabitaciones.size() - disponibles;
        long activas = listaReservas.stream().filter(r -> r.getEstado() == EstadoReserva.ACTIVA).count();
        BigDecimal ingresos = listaReservas.stream().filter(r -> r.getEstado() == EstadoReserva.FINALIZADA)
                .map(r -> r.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return "REPORTE GENERAL DEL HOTEL\n" +
               "Habitaciones registradas: " + listaHabitaciones.size() + "\n" +
               "Disponibles: " + disponibles + "\n" +
               "Reservadas: " + reservadas + "\n" +
               "Reservas activas: " + activas + "\n" +
               "Ingresos por estadías finalizadas: S/ " + ingresos.toPlainString() + "\n";
    }
}
