package com.hotel.pattern.creational;

import com.hotel.pattern.behavioral.TarifaStrategy;
import com.hotel.pattern.behavioral.TarifaSuiteStrategy;

public final class TarifaSuiteFactory extends TarifaFactory {
    @Override protected TarifaStrategy crearEstrategia() { return new TarifaSuiteStrategy(); }
}
