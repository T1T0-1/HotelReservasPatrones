package com.hotel.pattern.behavioral;

import com.hotel.domain.Reserva;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// GoF comportamiento: Observer.
public final class ReservaEventPublisher {
    private final List<ReservaObserver> observadores = new ArrayList<>();
    public void registrar(ReservaObserver observer) { observadores.add(observer); }
    public void publicar(String tipo, Reserva reserva) {
        ReservaEvent evento = new ReservaEvent(tipo, reserva, LocalDateTime.now());
        observadores.forEach(o -> o.actualizar(evento));
    }
}
