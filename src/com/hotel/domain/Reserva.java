package com.hotel.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class Reserva {
    private final String id;
    private final String huesped;
    private final String documento;
    private final String numeroHabitacion;
    private final LocalDate fechaEntrada;
    private final LocalDate fechaSalida;
    private final BigDecimal total;
    private EstadoReserva estado;

    public Reserva(String id, String huesped, String documento, String numeroHabitacion,
                   LocalDate fechaEntrada, LocalDate fechaSalida, BigDecimal total, EstadoReserva estado) {
        this.id = Objects.requireNonNull(id);
        this.huesped = Objects.requireNonNull(huesped);
        this.documento = Objects.requireNonNull(documento);
        this.numeroHabitacion = Objects.requireNonNull(numeroHabitacion);
        this.fechaEntrada = Objects.requireNonNull(fechaEntrada);
        this.fechaSalida = Objects.requireNonNull(fechaSalida);
        this.total = Objects.requireNonNull(total);
        this.estado = Objects.requireNonNull(estado);
    }

    public String getId() { return id; }
    public String getHuesped() { return huesped; }
    public String getDocumento() { return documento; }
    public String getNumeroHabitacion() { return numeroHabitacion; }
    public LocalDate getFechaEntrada() { return fechaEntrada; }
    public LocalDate getFechaSalida() { return fechaSalida; }
    public BigDecimal getTotal() { return total; }
    public EstadoReserva getEstado() { return estado; }

    // GRASP Information Expert: la reserva posee las fechas necesarias.
    public long calcularNoches() { return ChronoUnit.DAYS.between(fechaEntrada, fechaSalida); }

    public void cancelar() {
        if (estado != EstadoReserva.ACTIVA) throw new IllegalStateException("Solo se cancela una reserva activa");
        estado = EstadoReserva.CANCELADA;
    }

    public void finalizar() {
        if (estado != EstadoReserva.ACTIVA) throw new IllegalStateException("Solo se finaliza una reserva activa");
        estado = EstadoReserva.FINALIZADA;
    }
}
