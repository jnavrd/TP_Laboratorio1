package gui;

import dao.DAOProyecto;
import entidades.Proyecto;
import gui.estilos.ReporteListaRenderer;
import service.ServiceEmpleado;
import service.ServiceException;
import service.ServiceHistorialTrabajo;
import service.ServiceProyecto;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ReporteProyectos extends JPanel {
    ServiceProyecto serviceProyecto;
    ServiceHistorialTrabajo serviceHistorialTrabajo;
    ServiceEmpleado serviceEmpleado;
    PanelManager panel;
    DefaultListModel<Proyecto> listModelProyectos;
    JPanel reporteProyectos;

    public ReporteProyectos(PanelManager panel) throws ServiceException {
        this.panel = panel;
        armarReporte();
    }

    public void armarReporte() throws ServiceException {
        serviceProyecto = new ServiceProyecto(new DAOProyecto());
        setLayout(new BorderLayout());

        reporteProyectos = new JPanel();
        reporteProyectos.setLayout(new BoxLayout(reporteProyectos, BoxLayout.Y_AXIS));

        listModelProyectos = new DefaultListModel<>();
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        try {
            proyectos = serviceProyecto.buscarTodos();
            for(Proyecto proyecto: proyectos)
            {
                listModelProyectos.addElement(proyecto);
                proyecto.setearTareas();
            }
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        JScrollPane scrollPane = getjScrollPane();

        reporteProyectos.add(scrollPane);
        add(reporteProyectos, BorderLayout.CENTER);

    }

    protected JScrollPane getjScrollPane() {
        JList<Proyecto> list = new JList<>(listModelProyectos);
        list.setCellRenderer(new ReporteListaRenderer());

        JScrollPane scrollPane = new JScrollPane(list);
        return scrollPane;
    }

}
