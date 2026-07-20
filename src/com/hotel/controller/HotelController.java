package com.hotel.controller;

import com.hotel.application.NuevaReservaData;
import com.hotel.domain.Habitacion;
import com.hotel.domain.Reserva;
import com.hotel.domain.TipoHabitacion;
import com.hotel.pattern.behavioral.CancelarReservaCommand;
import com.hotel.pattern.structural.HotelFacade;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// GRASP Controller: recibe eventos de la vista y delega a la fachada.
public final class HotelController {
    private final HotelFacade facade;
    public HotelController(HotelFacade facade) { this.facade = facade; }

    public List<Habitacion> habitaciones() { return facade.listarHabitaciones(); }
    public List<Reserva> reservas() { return facade.listarReservas(); }
    public void registrarHabitacion(String numero, TipoHabitacion tipo, String precio) {
        facade.registrarHabitacion(numero, tipo, new BigDecimal(precio));
    }
    public void eliminarHabitacion(String numero) { facade.eliminarHabitacion(numero); }
    public Reserva reservar(String huesped, String documento, String habitacion, String entrada, String salida) {
        return facade.crearReserva(new NuevaReservaData(huesped, documento, habitacion,
                LocalDate.parse(entrada), LocalDate.parse(salida)));
    }
    public void cancelar(String id) { new CancelarReservaCommand(facade, id).ejecutar(); }
    public void finalizar(String id) { facade.finalizarReserva(id); }
    public String reporte() { return facade.generarReporte(); }
}
