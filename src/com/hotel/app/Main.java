package com.hotel.app;

import com.hotel.config.AppConfig;
import com.hotel.controller.HotelController;
import com.hotel.view.MainFrame;
import java.nio.file.Path;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class Main {
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) { }
        Path dataDir = Path.of(System.getProperty("data.dir", "data"));
        HotelController controller = new HotelController(AppConfig.crearFacade(dataDir));
        SwingUtilities.invokeLater(() -> new MainFrame(controller).setVisible(true));
    }
}
