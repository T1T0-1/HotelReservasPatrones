package com.hotel.pattern.behavioral;

import com.hotel.domain.Reserva;
import java.time.LocalDateTime;

public record ReservaEvent(String tipo, Reserva reserva, LocalDateTime fecha) { }
