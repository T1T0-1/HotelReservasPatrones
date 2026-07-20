package com.hotel.pattern;

import com.hotel.pattern.behavioral.TarifaSimpleStrategy;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifaStrategyTest {
    @Test void calculaPrecioPorNoches() {
        var strategy = new TarifaSimpleStrategy();
        assertEquals(new BigDecimal("360.00"), strategy.calcular(new BigDecimal("120.00"), 3));
    }
}
