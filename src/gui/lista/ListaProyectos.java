package gui.lista;

import dao.DAOProyecto;
import entidades.Proyecto;
import gui.PanelManager;
import gui.estilos.ProyectoListaRenderer;
import gui.lista.ListaObjeto;
import service.ServiceException;
import service.ServiceProyecto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//TODO
public class ListaProyectos extends ListaObjeto {
    ServiceProyecto serviceProyecto;
    PanelManager panel;
    DefaultListModel<Proyecto> listModelProyectos;
    JButton jButtonAddProyecto;

    public ListaProyectos(PanelManager panel) throws ServiceException {
        super(panel);
    }

    @Override
    protected void agregarElementosALista() throws ServiceException {
        serviceProyecto = new ServiceProyecto(new DAOProyecto());

        listModelProyectos = new DefaultListModel<>();

        ArrayList<Proyecto> proyectos = new ArrayList<>();
        proyectos = serviceProyecto.buscarTodos();
        for(Proyecto proyecto: proyectos)
        {
            listModelProyectos.addElement(proyecto);
        }
    }

    @Override
    protected JScrollPane getjScrollPane() {
        JList<Proyecto> list = new JList<>(listModelProyectos);
        list.setCellRenderer(new ProyectoListaRenderer());

        list.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                JList list1 = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = list1.locationToIndex(e.getPoint());
                    Proyecto proyecto = (Proyecto) list1.getModel().getElementAt(index);
                    try {
                        panel = new PanelManager(2, proyecto);
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

        jButtonAddProyecto = new JButton("Nuevo proyecto");
        //boton para agregar
        jButtonAddProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    panel = new PanelManager(3);
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        return jButtonAddProyecto;
    }

    @Override
    protected void refreshList() throws ServiceException {
   //limpio el list model
        listModelProyectos.clear();
        ArrayList<Proyecto> proyectos = serviceProyecto.buscarTodos();
        for(Proyecto proyecto: proyectos) {
            listModelProyectos.addElement(proyecto);
        }
    }
}
