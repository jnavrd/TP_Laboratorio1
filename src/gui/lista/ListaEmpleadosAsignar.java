package gui.lista;

import dao.DAOEmpleado;
import entidades.Empleado;
import entidades.Proyecto;
import gui.PanelManager;
import gui.estilos.EmpleadoListaRenderer;
import gui.lista.ListaEmpleados;
import service.ServiceEmpleado;
import service.ServiceException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ListaEmpleadosAsignar extends ListaEmpleados {
    public ListaEmpleadosAsignar(PanelManager panel, Proyecto proyecto) throws ServiceException {
        super(panel, proyecto);
    }

    protected void agregarElementosALista() throws ServiceException {
        serviceEmpleado = new ServiceEmpleado(new DAOEmpleado());

        listModelEmpleados = new DefaultListModel<>();

        ArrayList<Empleado> empleados = new ArrayList<>();
        empleados = serviceEmpleado.buscarSinAsignar();
        for(Empleado empleado: empleados)
        {
            listModelEmpleados.addElement(empleado);
            System.out.println("listaasignar" + empleado);
        }
    }

    protected JScrollPane getjScrollPane() {
        JList<Empleado> list = new JList<>(listModelEmpleados);
        list.setCellRenderer(new EmpleadoListaRenderer());

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList list1 = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list1.locationToIndex(e.getPoint());
                    Empleado empleado = (Empleado) list1.getModel().getElementAt(index);
                    try {
                        if(proyecto.getCantidadEmpleados() == proyecto.getEmpleados().size())
                        {
                            JOptionPane.showMessageDialog(null, "Todos los empleados necesarios fueron asignados");
                            return;
                        }
                        proyecto.agregarEmpleado(empleado);
                        empleado.setProyecto(proyecto);
                        serviceEmpleado.modificar(empleado);
                    } catch (ServiceException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        return scrollPane;
    }

    protected void refreshList() throws ServiceException {
        listModelEmpleados.clear();
        ArrayList<Empleado> empleados = serviceEmpleado.buscarSinAsignar();
        for(Empleado empleado: empleados) {
            listModelEmpleados.addElement(empleado);
        }
    }
}
