package com.hotel.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Habitacion {
    private final String numero;
    private final TipoHabitacion tipo;
    private BigDecimal precioBase;
    private EstadoHabitacion estado;

    public Habitacion(String numero, TipoHabitacion tipo, BigDecimal precioBase, EstadoHabitacion estado) {
        if (numero == null || numero.isBlank()) throw new IllegalArgumentException("Número obligatorio");
        if (precioBase == null || precioBase.signum() <= 0) throw new IllegalArgumentException("Precio inválido");
        this.numero = numero.trim();
        this.tipo = Objects.requireNonNull(tipo);
        this.precioBase = precioBase;
        this.estado = Objects.requireNonNull(estado);
    }

    public String getNumero() { return numero; }
    public TipoHabitacion getTipo() { return tipo; }
    public BigDecimal getPrecioBase() { return precioBase; }
    public EstadoHabitacion getEstado() { return estado; }

    public boolean estaDisponible() { return estado == EstadoHabitacion.DISPONIBLE; }

    public void reservar() {
        if (!estaDisponible()) throw new IllegalStateException("La habitación ya está reservada");
        estado = EstadoHabitacion.RESERVADA;
    }

    public void liberar() { estado = EstadoHabitacion.DISPONIBLE; }

    public void cambiarPrecio(BigDecimal nuevoPrecio) {
        if (nuevoPrecio == null || nuevoPrecio.signum() <= 0) throw new IllegalArgumentException("Precio inválido");
        precioBase = nuevoPrecio;
    }
}
