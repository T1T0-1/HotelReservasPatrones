package com.hotel.config;

import com.hotel.application.HabitacionService;
import com.hotel.application.ReporteService;
import com.hotel.application.ReservaService;
import com.hotel.infrastructure.csv.CsvHabitacionRepositoryAdapter;
import com.hotel.infrastructure.csv.CsvReservaRepositoryAdapter;
import com.hotel.infrastructure.csv.LegacyCsvStore;
import com.hotel.pattern.behavioral.AuditObserver;
import com.hotel.pattern.behavioral.ReservaEventPublisher;
import com.hotel.pattern.creational.TarifaFactoryProvider;
import com.hotel.pattern.structural.HotelFacade;
import java.nio.file.Path;

public final class AppConfig {
    private AppConfig() { }

    public static HotelFacade crearFacade(Path dataDir) {
        LegacyCsvStore store = new LegacyCsvStore();
        var habitacionesRepo = new CsvHabitacionRepositoryAdapter(store, dataDir.resolve("habitaciones.csv"));
        var reservasRepo = new CsvReservaRepositoryAdapter(store, dataDir.resolve("reservas.csv"));
        var publisher = new ReservaEventPublisher();
        publisher.registrar(new AuditObserver(dataDir.resolve("auditoria.log")));
        var habitaciones = new HabitacionService(habitacionesRepo, habitacionesRepo);
        var reservas = new ReservaService(habitacionesRepo, habitacionesRepo, reservasRepo, reservasRepo,
                new TarifaFactoryProvider(), publisher);
        var reportes = new ReporteService(habitacionesRepo, reservasRepo);
        return new HotelFacade(habitaciones, reservas, reportes);
    }
}
