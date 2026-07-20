package com.hotel.pattern.creational;

import com.hotel.domain.EstadoReserva;
import com.hotel.domain.Reserva;
import java.math.BigDecimal;
import java.time.LocalDate;

// GoF creacional: Builder.
public final class ReservaBuilder {
    private String id;
    private String huesped;
    private String documento;
    private String numeroHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private BigDecimal total;
    private EstadoReserva estado = EstadoReserva.ACTIVA;

    public ReservaBuilder id(String valor) { id = valor; return this; }
    public ReservaBuilder huesped(String valor) { huesped = valor; return this; }
    public ReservaBuilder documento(String valor) { documento = valor; return this; }
    public ReservaBuilder habitacion(String valor) { numeroHabitacion = valor; return this; }
    public ReservaBuilder entrada(LocalDate valor) { fechaEntrada = valor; return this; }
    public ReservaBuilder salida(LocalDate valor) { fechaSalida = valor; return this; }
    public ReservaBuilder total(BigDecimal valor) { total = valor; return this; }
    public ReservaBuilder estado(EstadoReserva valor) { estado = valor; return this; }

    public Reserva build() {
        return new Reserva(id, huesped, documento, numeroHabitacion, fechaEntrada, fechaSalida, total, estado);
    }
}
