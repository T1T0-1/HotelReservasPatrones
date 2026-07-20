package com.hotel.application;

import com.hotel.domain.EstadoHabitacion;
import com.hotel.domain.Habitacion;
import com.hotel.domain.TipoHabitacion;
import com.hotel.repository.HabitacionReadRepository;
import com.hotel.repository.HabitacionWriteRepository;
import java.math.BigDecimal;
import java.util.List;

public final class HabitacionService {
    private final HabitacionReadRepository lectura;
    private final HabitacionWriteRepository escritura;

    public HabitacionService(HabitacionReadRepository lectura, HabitacionWriteRepository escritura) {
        this.lectura = lectura; this.escritura = escritura;
    }

    public List<Habitacion> listar() { return lectura.listar(); }

    public void registrar(String numero, TipoHabitacion tipo, BigDecimal precio) {
        if (lectura.buscarPorNumero(numero).isPresent()) throw new IllegalArgumentException("La habitación ya existe");
        escritura.guardar(new Habitacion(numero, tipo, precio, EstadoHabitacion.DISPONIBLE));
    }

    public void eliminar(String numero) {
        Habitacion h = lectura.buscarPorNumero(numero).orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
        if (!h.estaDisponible()) throw new IllegalStateException("No se elimina una habitación reservada");
        escritura.eliminar(numero);
    }
}
