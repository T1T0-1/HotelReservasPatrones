package com.hotel.pattern.creational;

import com.hotel.pattern.behavioral.TarifaSimpleStrategy;
import com.hotel.pattern.behavioral.TarifaStrategy;

public final class TarifaSimpleFactory extends TarifaFactory {
    @Override protected TarifaStrategy crearEstrategia() { return new TarifaSimpleStrategy(); }
}
