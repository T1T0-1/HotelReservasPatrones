package com.hotel.pattern.creational;

import com.hotel.domain.TipoHabitacion;

public final class TarifaFactoryProvider {
    public TarifaFactory obtener(TipoHabitacion tipo) {
        return switch (tipo) {
            case SIMPLE -> new TarifaSimpleFactory();
            case DOBLE -> new TarifaDobleFactory();
            case SUITE -> new TarifaSuiteFactory();
        };
    }
}
