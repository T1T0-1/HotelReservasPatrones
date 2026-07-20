package com.hotel.application;

import com.hotel.domain.Habitacion;
import com.hotel.domain.Reserva;
import com.hotel.pattern.behavioral.ReservaEventPublisher;
import com.hotel.pattern.behavioral.TarifaStrategy;
import com.hotel.pattern.creational.ReservaBuilder;
import com.hotel.pattern.creational.TarifaFactoryProvider;
import com.hotel.repository.HabitacionReadRepository;
import com.hotel.repository.HabitacionWriteRepository;
import com.hotel.repository.ReservaReadRepository;
import com.hotel.repository.ReservaWriteRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public final class ReservaService {
    private final HabitacionReadRepository habitacionesLectura;
    private final HabitacionWriteRepository habitacionesEscritura;
    private final ReservaReadRepository reservasLectura;
    private final ReservaWriteRepository reservasEscritura;
    private final TarifaFactoryProvider factoryProvider;
    private final ReservaEventPublisher publisher;

    public ReservaService(HabitacionReadRepository habitacionesLectura,
                          HabitacionWriteRepository habitacionesEscritura,
                          ReservaReadRepository reservasLectura,
                          ReservaWriteRepository reservasEscritura,
                          TarifaFactoryProvider factoryProvider,
                          ReservaEventPublisher publisher) {
        this.habitacionesLectura = habitacionesLectura;
        this.habitacionesEscritura = habitacionesEscritura;
        this.reservasLectura = reservasLectura;
        this.reservasEscritura = reservasEscritura;
        this.factoryProvider = factoryProvider;
        this.publisher = publisher;
    }

    public List<Reserva> listar() { return reservasLectura.listar(); }

    public Reserva crear(NuevaReservaData data) {
        validar(data);
        Habitacion habitacion = habitacionesLectura.buscarPorNumero(data.numeroHabitacion())
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
        if (!habitacion.estaDisponible()) throw new IllegalStateException("Habitación no disponible");

        long noches = ChronoUnit.DAYS.between(data.entrada(), data.salida());
        TarifaStrategy strategy = factoryProvider.obtener(habitacion.getTipo()).crear();
        BigDecimal total = strategy.calcular(habitacion.getPrecioBase(), noches);

        Reserva reserva = new ReservaBuilder()
                .id(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .huesped(data.huesped().trim()).documento(data.documento().trim())
                .habitacion(habitacion.getNumero()).entrada(data.entrada()).salida(data.salida())
                .total(total).build();

        habitacion.reservar();
        habitacionesEscritura.guardar(habitacion);
        try {
            reservasEscritura.guardar(reserva);
        } catch (RuntimeException ex) {
            habitacion.liberar();
            habitacionesEscritura.guardar(habitacion);
            throw ex;
        }
        publisher.publicar("RESERVA_CREADA", reserva);
        return reserva;
    }

    public void cancelar(String id) {
        Reserva reserva = reservaActiva(id);
        Habitacion habitacion = habitacionDe(reserva);
        reserva.cancelar();
        habitacion.liberar();
        reservasEscritura.guardar(reserva);
        habitacionesEscritura.guardar(habitacion);
        publisher.publicar("RESERVA_CANCELADA", reserva);
    }

    public void finalizar(String id) {
        Reserva reserva = reservaActiva(id);
        Habitacion habitacion = habitacionDe(reserva);
        reserva.finalizar();
        habitacion.liberar();
        reservasEscritura.guardar(reserva);
        habitacionesEscritura.guardar(habitacion);
        publisher.publicar("ESTADIA_FINALIZADA", reserva);
    }

    private Reserva reservaActiva(String id) {
        return reservasLectura.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
    }

    private Habitacion habitacionDe(Reserva reserva) {
        return habitacionesLectura.buscarPorNumero(reserva.getNumeroHabitacion())
                .orElseThrow(() -> new IllegalStateException("Habitación asociada no encontrada"));
    }

    private void validar(NuevaReservaData data) {
        if (data == null) throw new IllegalArgumentException("Datos obligatorios");
        if (data.huesped() == null || data.huesped().isBlank()) throw new IllegalArgumentException("Huésped obligatorio");
        if (data.documento() == null || data.documento().isBlank()) throw new IllegalArgumentException("Documento obligatorio");
        if (data.numeroHabitacion() == null || data.numeroHabitacion().isBlank()) throw new IllegalArgumentException("Habitación obligatoria");
        LocalDate hoy = LocalDate.now();
        if (data.entrada() == null || data.salida() == null) throw new IllegalArgumentException("Fechas obligatorias");
        if (data.entrada().isBefore(hoy)) throw new IllegalArgumentException("La entrada no puede ser anterior a hoy");
        if (!data.salida().isAfter(data.entrada())) throw new IllegalArgumentException("La salida debe ser posterior a la entrada");
    }
}
