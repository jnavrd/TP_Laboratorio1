package gui.lista;

import gui.PanelManager;
import service.ServiceException;

import javax.swing.*;
import java.awt.*;

public abstract class ListaObjeto extends JPanel {

    Object objeto;
    PanelManager panel;
    JPanel listaObjeto;
    DefaultListModel<Object> listModelObjeto;
    JButton jButtonAddObjeto, jButtonRefrescar;
    public ListaObjeto(PanelManager panel) throws ServiceException {
        this.panel = panel;
        armarLista();
    }

    public ListaObjeto(PanelManager panel, Object objeto) throws ServiceException {
        this.objeto = objeto;
        this.panel = panel;
    }

    public void armarLista() throws ServiceException {
        setLayout(new BorderLayout());

        listaObjeto = new JPanel();
        listaObjeto.setLayout(new BoxLayout(listaObjeto, BoxLayout.Y_AXIS));

        agregarElementosALista();

        jButtonAddObjeto = botonAddNuevo();

        jButtonRefrescar = new JButton("Refrescar");
        //recargar la lista
        jButtonRefrescar.addActionListener(e -> {
            try {
                refreshList();
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });
        JPanel panelBotones = new JPanel(new GridLayout(1, 2));
        panelBotones.add(jButtonAddObjeto);
        panelBotones.add(jButtonRefrescar);

        JScrollPane scrollPane = getjScrollPane();

        listaObjeto.add(panelBotones);
        listaObjeto.add(scrollPane);
        add(listaObjeto, BorderLayout.CENTER);


    }

    protected abstract JScrollPane getjScrollPane();
    protected abstract void agregarElementosALista() throws ServiceException;
    protected abstract JButton botonAddNuevo();
    protected abstract void refreshList() throws ServiceException;

}
