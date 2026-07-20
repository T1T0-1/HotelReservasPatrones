package com.hotel.pattern.behavioral;

import com.hotel.pattern.structural.HotelFacade;

// Patrón extra: Command.
public final class CancelarReservaCommand implements Command {
    private final HotelFacade facade;
    private final String reservaId;
    public CancelarReservaCommand(HotelFacade facade, String reservaId) { this.facade = facade; this.reservaId = reservaId; }
    @Override public void ejecutar() { facade.cancelarReserva(reservaId); }
}
