package com.hotel.pattern.structural;

import com.hotel.application.HabitacionService;
import com.hotel.application.NuevaReservaData;
import com.hotel.application.ReporteService;
import com.hotel.application.ReservaService;
import com.hotel.domain.Habitacion;
import com.hotel.domain.Reserva;
import com.hotel.domain.TipoHabitacion;
import java.math.BigDecimal;
import java.util.List;

// GoF estructural: Facade. Simplifica el acceso de la interfaz a los casos de uso.
public final class HotelFacade {
    private final HabitacionService habitaciones;
    private final ReservaService reservas;
    private final ReporteService reportes;

    public HotelFacade(HabitacionService habitaciones, ReservaService reservas, ReporteService reportes) {
        this.habitaciones = habitaciones; this.reservas = reservas; this.reportes = reportes;
    }

    public List<Habitacion> listarHabitaciones() { return habitaciones.listar(); }
    public void registrarHabitacion(String numero, TipoHabitacion tipo, BigDecimal precio) { habitaciones.registrar(numero, tipo, precio); }
    public void eliminarHabitacion(String numero) { habitaciones.eliminar(numero); }
    public List<Reserva> listarReservas() { return reservas.listar(); }
    public Reserva crearReserva(NuevaReservaData data) { return reservas.crear(data); }
    public void cancelarReserva(String id) { reservas.cancelar(id); }
    public void finalizarReserva(String id) { reservas.finalizar(id); }
    public String generarReporte() { return reportes.generarResumen(); }
}
