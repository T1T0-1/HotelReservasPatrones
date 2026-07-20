package com.hotel.view;

import com.hotel.controller.HotelController;
import com.hotel.domain.Habitacion;
import com.hotel.domain.Reserva;
import com.hotel.domain.TipoHabitacion;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public final class MainFrame extends JFrame {
    private final HotelController controller;
    private final DefaultTableModel modeloHabitaciones = new DefaultTableModel(
            new Object[]{"Número", "Tipo", "Precio", "Estado"}, 0) { public boolean isCellEditable(int r, int c){ return false; }};
    private final DefaultTableModel modeloReservas = new DefaultTableModel(
            new Object[]{"ID", "Huésped", "Documento", "Habitación", "Entrada", "Salida", "Total", "Estado"}, 0) { public boolean isCellEditable(int r, int c){ return false; }};
    private final JTable tablaHabitaciones = new JTable(modeloHabitaciones);
    private final JTable tablaReservas = new JTable(modeloReservas);
    private final JTextArea reporte = new JTextArea();

    public MainFrame(HotelController controller) {
        super("Hotel - Reservas y Habitaciones");
        this.controller = controller;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(980, 600);
        setLocationRelativeTo(null);
        add(crearContenido());
        actualizarTodo();
    }

    private JComponent crearContenido() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Habitaciones", panelHabitaciones());
        tabs.addTab("Reservas", panelReservas());
        tabs.addTab("Reporte", panelReporte());
        return tabs;
    }

    private JPanel panelHabitaciones() {
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(new JScrollPane(tablaHabitaciones), BorderLayout.CENTER);
        JTextField numero = new JTextField(6);
        JComboBox<TipoHabitacion> tipo = new JComboBox<>(TipoHabitacion.values());
        JTextField precio = new JTextField("120.00", 7);
        JButton agregar = new JButton("Agregar");
        JButton eliminar = new JButton("Eliminar seleccionada");
        JButton refrescar = new JButton("Actualizar");
        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        acciones.add(new JLabel("Número:")); acciones.add(numero);
        acciones.add(new JLabel("Tipo:")); acciones.add(tipo);
        acciones.add(new JLabel("Precio:")); acciones.add(precio);
        acciones.add(agregar); acciones.add(eliminar); acciones.add(refrescar);
        agregar.addActionListener(e -> ejecutar(() -> {
            controller.registrarHabitacion(numero.getText(), (TipoHabitacion) tipo.getSelectedItem(), precio.getText());
            numero.setText(""); actualizarTodo();
        }));
        eliminar.addActionListener(e -> ejecutar(() -> {
            int fila = tablaHabitaciones.getSelectedRow();
            if (fila < 0) throw new IllegalArgumentException("Seleccione una habitación");
            controller.eliminarHabitacion(modeloHabitaciones.getValueAt(fila,0).toString()); actualizarTodo();
        }));
        refrescar.addActionListener(e -> actualizarTodo());
        panel.add(acciones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel panelReservas() {
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(new JScrollPane(tablaReservas), BorderLayout.CENTER);
        JTextField huesped = new JTextField(12);
        JTextField documento = new JTextField(8);
        JTextField habitacion = new JTextField("101", 5);
        JTextField entrada = new JTextField(LocalDate.now().plusDays(1).toString(), 10);
        JTextField salida = new JTextField(LocalDate.now().plusDays(2).toString(), 10);
        JPanel datos = new JPanel(new GridLayout(2,5,5,5));
        datos.add(etiquetaCampo("Huésped", huesped));
        datos.add(etiquetaCampo("Documento", documento));
        datos.add(etiquetaCampo("Habitación", habitacion));
        datos.add(etiquetaCampo("Entrada AAAA-MM-DD", entrada));
        datos.add(etiquetaCampo("Salida AAAA-MM-DD", salida));
        JButton reservar = new JButton("Registrar reserva");
        JButton cancelar = new JButton("Cancelar seleccionada");
        JButton finalizar = new JButton("Finalizar estadía");
        JButton refrescar = new JButton("Actualizar");
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botones.add(reservar); botones.add(cancelar); botones.add(finalizar); botones.add(refrescar);
        JPanel inferior = new JPanel(new BorderLayout()); inferior.add(datos, BorderLayout.CENTER); inferior.add(botones, BorderLayout.SOUTH);
        panel.add(inferior, BorderLayout.SOUTH);
        reservar.addActionListener(e -> ejecutar(() -> {
            Reserva r = controller.reservar(huesped.getText(), documento.getText(), habitacion.getText(), entrada.getText(), salida.getText());
            JOptionPane.showMessageDialog(this, "Reserva creada: " + r.getId() + " | Total S/ " + r.getTotal());
            actualizarTodo();
        }));
        cancelar.addActionListener(e -> ejecutar(() -> { controller.cancelar(idReservaSeleccionada()); actualizarTodo(); }));
        finalizar.addActionListener(e -> ejecutar(() -> { controller.finalizar(idReservaSeleccionada()); actualizarTodo(); }));
        refrescar.addActionListener(e -> actualizarTodo());
        return panel;
    }

    private JPanel etiquetaCampo(String texto, JTextField campo) {
        JPanel p = new JPanel(new BorderLayout(2,2)); p.add(new JLabel(texto), BorderLayout.NORTH); p.add(campo); return p;
    }

    private JPanel panelReporte() {
        JPanel panel = new JPanel(new BorderLayout(8,8));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        reporte.setEditable(false); reporte.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
        JButton generar = new JButton("Generar reporte");
        generar.addActionListener(e -> reporte.setText(controller.reporte()));
        panel.add(new JScrollPane(reporte)); panel.add(generar, BorderLayout.SOUTH); return panel;
    }

    private String idReservaSeleccionada() {
        int fila = tablaReservas.getSelectedRow();
        if (fila < 0) throw new IllegalArgumentException("Seleccione una reserva");
        return modeloReservas.getValueAt(fila,0).toString();
    }

    private void actualizarTodo() {
        try {
            modeloHabitaciones.setRowCount(0);
            for (Habitacion h : controller.habitaciones()) modeloHabitaciones.addRow(new Object[]{h.getNumero(), h.getTipo(), h.getPrecioBase(), h.getEstado()});
            modeloReservas.setRowCount(0);
            for (Reserva r : controller.reservas()) modeloReservas.addRow(new Object[]{r.getId(), r.getHuesped(), r.getDocumento(), r.getNumeroHabitacion(), r.getFechaEntrada(), r.getFechaSalida(), r.getTotal(), r.getEstado()});
            reporte.setText(controller.reporte());
        } catch (RuntimeException ex) { mostrarError(ex); }
    }

    private void ejecutar(Runnable accion) {
        try { accion.run(); } catch (RuntimeException ex) { mostrarError(ex); }
    }

    private void mostrarError(RuntimeException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
