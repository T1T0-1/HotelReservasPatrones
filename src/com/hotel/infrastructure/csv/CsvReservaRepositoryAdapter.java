package com.hotel.infrastructure.csv;

import com.hotel.domain.EstadoReserva;
import com.hotel.domain.Reserva;
import com.hotel.pattern.creational.ReservaBuilder;
import com.hotel.repository.ReservaReadRepository;
import com.hotel.repository.ReservaWriteRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// GoF estructural: Adapter.
public final class CsvReservaRepositoryAdapter implements ReservaReadRepository, ReservaWriteRepository {
    private final LegacyCsvStore store;
    private final Path archivo;

    public CsvReservaRepositoryAdapter(LegacyCsvStore store, Path archivo) {
        this.store = store; this.archivo = archivo;
    }

    @Override public List<Reserva> listar() {
        try {
            List<Reserva> resultado = new ArrayList<>();
            for (String linea : store.load(archivo)) resultado.add(desdeLinea(linea));
            return resultado;
        } catch (IOException ex) { throw new PersistenceException("Error al leer reservas", ex); }
    }

    @Override public Optional<Reserva> buscarPorId(String id) {
        return listar().stream().filter(r -> r.getId().equalsIgnoreCase(id)).findFirst();
    }

    @Override public void guardar(Reserva reserva) {
        List<Reserva> datos = new ArrayList<>(listar());
        datos.removeIf(r -> r.getId().equalsIgnoreCase(reserva.getId()));
        datos.add(reserva);
        escribir(datos);
    }

    @Override public void eliminar(String id) {
        List<Reserva> datos = new ArrayList<>(listar());
        datos.removeIf(r -> r.getId().equalsIgnoreCase(id));
        escribir(datos);
    }

    private void escribir(List<Reserva> datos) {
        try { store.save(archivo, datos.stream().map(this::aLinea).toList()); }
        catch (IOException ex) { throw new PersistenceException("Error al guardar reservas", ex); }
    }

    private String aLinea(Reserva r) {
        return String.join(";", r.getId(), limpiar(r.getHuesped()), limpiar(r.getDocumento()),
                r.getNumeroHabitacion(), r.getFechaEntrada().toString(), r.getFechaSalida().toString(),
                r.getTotal().toPlainString(), r.getEstado().name());
    }

    private Reserva desdeLinea(String linea) {
        String[] p = linea.split(";", -1);
        if (p.length != 8) throw new PersistenceException("Línea de reserva inválida: " + linea);
        return new ReservaBuilder().id(p[0]).huesped(p[1]).documento(p[2]).habitacion(p[3])
                .entrada(LocalDate.parse(p[4])).salida(LocalDate.parse(p[5]))
                .total(new BigDecimal(p[6])).estado(EstadoReserva.valueOf(p[7])).build();
    }

    private String limpiar(String texto) { return texto.replace(';', ' ').trim(); }
}
