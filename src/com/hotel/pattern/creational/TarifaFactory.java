package com.hotel.pattern.creational;

import com.hotel.pattern.behavioral.TarifaStrategy;

// GoF creacional: Factory Method.
public abstract class TarifaFactory {
    public final TarifaStrategy crear() { return crearEstrategia(); }
    protected abstract TarifaStrategy crearEstrategia();
}
