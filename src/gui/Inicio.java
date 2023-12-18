package gui;

import service.ServiceException;

import javax.swing.*;
import java.awt.*;

public class Inicio extends JPanel {
    PanelManager panel;
    JButton proyectosButton, empleadosButton, reporteButton;

    public Inicio(PanelManager panel) {
        this.panel = panel;
        armarInicio();
    }

    public void armarInicio() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Administrador de Proyectos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        proyectosButton = new JButton("Proyectos");
        proyectosButton.addActionListener(e -> {
            try {
                panel = new PanelManager(2);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });

        empleadosButton = new JButton("Empleados");
        empleadosButton.addActionListener(e -> {
            try {
                panel = new PanelManager(4);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });

        reporteButton = new JButton("Reporte Proyectos");
        reporteButton.addActionListener(e -> {
            try {
                panel = new PanelManager(6);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });

        add(titulo, BorderLayout.NORTH);
        panelBotones.add(proyectosButton);
        panelBotones.add(empleadosButton);
        panelBotones.add(reporteButton);

        add(panelBotones, BorderLayout.CENTER);

        setPreferredSize(new Dimension(400, 300));
    }
}
