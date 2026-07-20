package com.hotel.infrastructure.csv;

import com.hotel.domain.EstadoHabitacion;
import com.hotel.domain.Habitacion;
import com.hotel.domain.TipoHabitacion;
import com.hotel.repository.HabitacionReadRepository;
import com.hotel.repository.HabitacionWriteRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// GoF estructural: Adapter. Convierte LegacyCsvStore al contrato Repository.
public final class CsvHabitacionRepositoryAdapter implements HabitacionReadRepository, HabitacionWriteRepository {
    private final LegacyCsvStore store;
    private final Path archivo;

    public CsvHabitacionRepositoryAdapter(LegacyCsvStore store, Path archivo) {
        this.store = store; this.archivo = archivo;
    }

    @Override public List<Habitacion> listar() {
        try {
            List<Habitacion> resultado = new ArrayList<>();
            for (String linea : store.load(archivo)) resultado.add(desdeLinea(linea));
            return resultado;
        } catch (IOException ex) { throw new PersistenceException("Error al leer habitaciones", ex); }
    }

    @Override public Optional<Habitacion> buscarPorNumero(String numero) {
        return listar().stream().filter(h -> h.getNumero().equalsIgnoreCase(numero)).findFirst();
    }

    @Override public void guardar(Habitacion habitacion) {
        List<Habitacion> datos = new ArrayList<>(listar());
        datos.removeIf(h -> h.getNumero().equalsIgnoreCase(habitacion.getNumero()));
        datos.add(habitacion);
        datos.sort((a,b) -> a.getNumero().compareToIgnoreCase(b.getNumero()));
        escribir(datos);
    }

    @Override public void eliminar(String numero) {
        List<Habitacion> datos = new ArrayList<>(listar());
        datos.removeIf(h -> h.getNumero().equalsIgnoreCase(numero));
        escribir(datos);
    }

    private void escribir(List<Habitacion> datos) {
        try { store.save(archivo, datos.stream().map(this::aLinea).toList()); }
        catch (IOException ex) { throw new PersistenceException("Error al guardar habitaciones", ex); }
    }

    private String aLinea(Habitacion h) {
        return String.join(";", h.getNumero(), h.getTipo().name(), h.getPrecioBase().toPlainString(), h.getEstado().name());
    }

    private Habitacion desdeLinea(String linea) {
        String[] p = linea.split(";", -1);
        if (p.length != 4) throw new PersistenceException("Línea de habitación inválida: " + linea);
        return new Habitacion(p[0], TipoHabitacion.valueOf(p[1]), new BigDecimal(p[2]), EstadoHabitacion.valueOf(p[3]));
    }
}
