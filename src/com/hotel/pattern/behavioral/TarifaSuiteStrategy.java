package com.hotel.pattern.behavioral;

import java.math.BigDecimal;

public final class TarifaSuiteStrategy implements TarifaStrategy {
    @Override public BigDecimal calcular(BigDecimal precioBase, long noches) {
        return precioBase.multiply(BigDecimal.valueOf(noches));
    }
}
