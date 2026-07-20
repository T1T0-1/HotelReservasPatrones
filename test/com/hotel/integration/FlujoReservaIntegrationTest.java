package com.hotel.integration;

import com.hotel.application.NuevaReservaData;
import com.hotel.config.AppConfig;
import com.hotel.domain.EstadoHabitacion;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class FlujoReservaIntegrationTest {
    @TempDir Path dir;

    @Test void flujoCompletoPersistido() throws Exception {
        Files.writeString(dir.resolve("habitaciones.csv"), "101;SIMPLE;120.00;DISPONIBLE\n");
        Files.writeString(dir.resolve("reservas.csv"), "");
        var facade = AppConfig.crearFacade(dir);
        var reserva = facade.crearReserva(new NuevaReservaData("Luis", "999", "101", LocalDate.now().plusDays(1), LocalDate.now().plusDays(2)));
        assertEquals(EstadoHabitacion.RESERVADA, facade.listarHabitaciones().get(0).getEstado());
        facade.finalizarReserva(reserva.getId());
        assertEquals(EstadoHabitacion.DISPONIBLE, facade.listarHabitaciones().get(0).getEstado());
        assertFalse(Files.readString(dir.resolve("reservas.csv")).isBlank());
    }
}
