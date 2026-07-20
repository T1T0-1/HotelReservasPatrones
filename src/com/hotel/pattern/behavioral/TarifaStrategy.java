package com.hotel.pattern.behavioral;

import java.math.BigDecimal;

// GoF comportamiento: Strategy.
public interface TarifaStrategy {
    BigDecimal calcular(BigDecimal precioBase, long noches);
}
