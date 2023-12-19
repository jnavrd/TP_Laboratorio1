package gui.lista;

import dao.DAOEmpleado;
import entidades.Empleado;
import entidades.Proyecto;
import gui.PanelManager;
import gui.estilos.EmpleadoListaRenderer;
import gui.estilos.UtilidadEstilo;
import gui.lista.ListaObjeto;
import service.ServiceEmpleado;
import service.ServiceException;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ListaEmpleados extends ListaObjeto {
    protected ServiceEmpleado serviceEmpleado;
    PanelManager panel;
    protected Proyecto proyecto;
    protected DefaultListModel<Empleado> listModelEmpleados;
    JButton jButtonAddEmpleado;
    UtilidadEstilo estilo;
    public ListaEmpleados(PanelManager panel) throws ServiceException {
        super(panel);
    }

    public ListaEmpleados(PanelManager panel, Proyecto proyecto) throws ServiceException {
        super(panel, proyecto);
        this.proyecto = proyecto;
        armarLista();
    }

    @Override
    protected void agregarElementosALista() throws ServiceException {
        serviceEmpleado = new ServiceEmpleado(new DAOEmpleado());

        listModelEmpleados = new DefaultListModel<>();

        ArrayList<Empleado> empleados = new ArrayList<>();
        empleados = serviceEmpleado.buscarTodos();
        for(Empleado empleado: empleados)
        {
            listModelEmpleados.addElement(empleado);
            System.out.println("lista empleados" + empleado);
        }
    }

    @Override
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
                        panel = new PanelManager(5, empleado);
                    } catch (ServiceException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        return scrollPane;
    }

    @Override
    protected JButton botonAddNuevo() {
        jButtonAddEmpleado = new JButton("Nuevo Empleado");
        jButtonAddEmpleado.addActionListener(e -> {
            try {
                panel = new PanelManager(5);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });
        return jButtonAddEmpleado;
    }

    @Override
    protected void refreshList() throws ServiceException {
        listModelEmpleados.clear();
        ArrayList<Empleado> empleados = serviceEmpleado.buscarTodos();
        for(Empleado empleado: empleados) {
            listModelEmpleados.addElement(empleado);
        }
    }
}
