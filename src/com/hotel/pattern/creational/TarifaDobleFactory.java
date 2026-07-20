package com.hotel.pattern.creational;

import com.hotel.pattern.behavioral.TarifaDobleStrategy;
import com.hotel.pattern.behavioral.TarifaStrategy;

public final class TarifaDobleFactory extends TarifaFactory {
    @Override protected TarifaStrategy crearEstrategia() { return new TarifaDobleStrategy(); }
}
