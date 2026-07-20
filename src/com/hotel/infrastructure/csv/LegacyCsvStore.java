package com.hotel.infrastructure.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

// Componente simple con una interfaz propia que será adaptada a repositorios.
public final class LegacyCsvStore {
    public List<String> load(Path archivo) throws IOException {
        if (!Files.exists(archivo)) return List.of();
        return Files.readAllLines(archivo, StandardCharsets.UTF_8).stream()
                .filter(linea -> !linea.isBlank()).toList();
    }

    public void save(Path archivo, List<String> lineas) throws IOException {
        Files.createDirectories(archivo.getParent());
        Path temporal = archivo.resolveSibling(archivo.getFileName() + ".tmp");
        Files.write(temporal, lineas, StandardCharsets.UTF_8);
        Files.move(temporal, archivo, StandardCopyOption.REPLACE_EXISTING);
    }
}
