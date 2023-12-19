package gui.lista;
import dao.DAOTarea;
import entidades.Proyecto;
import entidades.Tarea;
import gui.PanelManager;
import gui.estilos.TareaListaRenderer;
import gui.lista.ListaObjeto;
import service.ServiceException;
import service.ServiceTarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//dependencias  serviceTarea, PanelManager
public class ListaTareas extends ListaObjeto {
    ServiceTarea serviceTarea;
    PanelManager panel;
    DefaultListModel<Tarea> listModelTareas;
    JButton jButtonAddTarea, jButtonAssignEmpleado;
    Proyecto proyecto;

    public ListaTareas(PanelManager panel, Proyecto proyecto) throws ServiceException {
        super(panel, proyecto);
        this.proyecto = proyecto;
        armarLista();
        botonAsignarEmpleados();
    }

    @Override
    protected void agregarElementosALista() throws ServiceException {

        serviceTarea = new ServiceTarea(new DAOTarea());

        listModelTareas = new DefaultListModel<>();

        ArrayList<Tarea> tareas = new ArrayList<>();
        tareas = serviceTarea.buscarEnProyecto(proyecto.getId());

        for(Tarea tarea: tareas)
        {
            listModelTareas.addElement(tarea);
            tarea.setProyecto(proyecto);
        }

    }

    protected JScrollPane getjScrollPane() {
        JList<Tarea> list = new JList<>(listModelTareas);
        list.setCellRenderer(new TareaListaRenderer());

        list.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JList list1 = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list1.locationToIndex(e.getPoint());
                    Tarea tarea = (Tarea) list1.getModel().getElementAt(index);
                    tarea.setProyecto(proyecto);
                    proyecto.agregarTarea(tarea);
                    try {
                        panel = new PanelManager(3, tarea);
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
        jButtonAddTarea = new JButton("Nueva tarea");
        //boton para agregar tarea abre formulario tarea
        System.out.println("listaTareas add nuevo" + proyecto);
        jButtonAddTarea.addActionListener(e -> {
            try {
                panel = new PanelManager(1, proyecto);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });
        return jButtonAddTarea;
    }

    protected void botonAsignarEmpleados() {
        jButtonAssignEmpleado = new JButton("Asignar Empleados");
        jButtonAssignEmpleado.addActionListener(e -> {
            try {
                panel = new PanelManager(4, proyecto);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });
        add(jButtonAssignEmpleado, BorderLayout.SOUTH);
    }


    @Override
    protected void refreshList() throws ServiceException {
        //limpio el list model
        listModelTareas.clear();

        ArrayList<Tarea> tareas = serviceTarea.buscarEnProyecto(proyecto.getId());
        for(Tarea tarea: tareas) {
            listModelTareas.addElement(tarea);
        }
    }
}
