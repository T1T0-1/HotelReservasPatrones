package com.hotel.integration;

import com.hotel.domain.EstadoHabitacion;
import com.hotel.domain.Habitacion;
import com.hotel.domain.TipoHabitacion;
import com.hotel.infrastructure.csv.CsvHabitacionRepositoryAdapter;
import com.hotel.infrastructure.csv.LegacyCsvStore;
import java.math.BigDecimal;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvRepositoryIntegrationTest {
    @TempDir Path dir;

    @Test void guardaYRecuperaHabitacion() {
        var repo = new CsvHabitacionRepositoryAdapter(new LegacyCsvStore(), dir.resolve("habitaciones.csv"));
        repo.guardar(new Habitacion("301", TipoHabitacion.SUITE, new BigDecimal("350.00"), EstadoHabitacion.DISPONIBLE));
        assertTrue(repo.buscarPorNumero("301").isPresent());
        assertEquals(1, repo.listar().size());
    }
}
