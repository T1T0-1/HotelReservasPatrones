package com.hotel.pattern.behavioral;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AuditObserver implements ReservaObserver {
    private static final Logger LOG = Logger.getLogger(AuditObserver.class.getName());
    private final Path archivo;

    public AuditObserver(Path archivo) { this.archivo = archivo; }

    @Override public void actualizar(ReservaEvent evento) {
        String linea = "%s | %s | %s | habitación %s%n".formatted(
                evento.fecha(), evento.tipo(), evento.reserva().getId(), evento.reserva().getNumeroHabitacion());
        try {
            Files.createDirectories(archivo.getParent());
            Files.writeString(archivo, linea, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "No se pudo escribir auditoría", ex);
        }
    }
}
