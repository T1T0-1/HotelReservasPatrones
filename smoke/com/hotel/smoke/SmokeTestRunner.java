package com.hotel.smoke;

import com.hotel.application.NuevaReservaData;
import com.hotel.config.AppConfig;
import com.hotel.domain.EstadoHabitacion;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public final class SmokeTestRunner {
    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("hotel-smoke-");
        Files.writeString(dir.resolve("habitaciones.csv"), "101;SIMPLE;120.00;DISPONIBLE\n");
        Files.writeString(dir.resolve("reservas.csv"), "");
        var facade = AppConfig.crearFacade(dir);
        var reserva = facade.crearReserva(new NuevaReservaData("Ana Torres", "12345678", "101",
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
        comprobar(reserva.getTotal().toPlainString().equals("240.00"), "cálculo de total");
        comprobar(facade.listarHabitaciones().get(0).getEstado() == EstadoHabitacion.RESERVADA, "habitación reservada");
        facade.cancelarReserva(reserva.getId());
        comprobar(facade.listarHabitaciones().get(0).getEstado() == EstadoHabitacion.DISPONIBLE, "habitación liberada");
        comprobar(Files.readString(dir.resolve("auditoria.log")).contains("RESERVA_CREADA"), "auditoría observer");
        System.out.println("SMOKE TEST OK: flujo crear-reservar-cancelar-persistir.");
    }

    private static void comprobar(boolean condicion, String mensaje) {
        if (!condicion) throw new AssertionError("Falló: " + mensaje);
    }
}
