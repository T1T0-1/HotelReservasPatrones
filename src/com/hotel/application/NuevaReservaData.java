package com.hotel.application;

import java.time.LocalDate;

public record NuevaReservaData(String huesped, String documento, String numeroHabitacion,
                               LocalDate entrada, LocalDate salida) { }
