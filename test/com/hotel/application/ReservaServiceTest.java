package com.hotel.application;

import com.hotel.domain.EstadoHabitacion;
import com.hotel.domain.Habitacion;
import com.hotel.domain.TipoHabitacion;
import com.hotel.pattern.behavioral.ReservaEventPublisher;
import com.hotel.pattern.creational.TarifaFactoryProvider;
import com.hotel.repository.HabitacionReadRepository;
import com.hotel.repository.HabitacionWriteRepository;
import com.hotel.repository.ReservaReadRepository;
import com.hotel.repository.ReservaWriteRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReservaServiceTest {
    @Test void guardaReservaYMarcaHabitacion() {
        HabitacionReadRepository hRead = mock(HabitacionReadRepository.class);
        HabitacionWriteRepository hWrite = mock(HabitacionWriteRepository.class);
        ReservaReadRepository rRead = mock(ReservaReadRepository.class);
        ReservaWriteRepository rWrite = mock(ReservaWriteRepository.class);
        Habitacion h = new Habitacion("101", TipoHabitacion.SIMPLE, new BigDecimal("120.00"), EstadoHabitacion.DISPONIBLE);
        when(hRead.buscarPorNumero("101")).thenReturn(Optional.of(h));
        var service = new ReservaService(hRead, hWrite, rRead, rWrite, new TarifaFactoryProvider(), new ReservaEventPublisher());
        var reserva = service.crear(new NuevaReservaData("Ana", "123", "101", LocalDate.now().plusDays(1), LocalDate.now().plusDays(3)));
        assertEquals(new BigDecimal("240.00"), reserva.getTotal());
        verify(hWrite).guardar(h);
        verify(rWrite).guardar(any());
    }
}
