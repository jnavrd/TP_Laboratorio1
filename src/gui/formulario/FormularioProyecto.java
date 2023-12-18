package gui.formulario;

import dao.DAOProyecto;
import entidades.Proyecto;
import gui.PanelManager;
import gui.estilos.UtilidadEstilo;
import service.ServiceException;
import service.ServiceProyecto;

import javax.swing.*;
import java.awt.*;

public class FormularioProyecto extends JPanel implements Formulario {
    Proyecto proyecto;
    ServiceProyecto serviceProyecto;
    PanelManager panel; //contenedor
    JPanel formularioProyecto; //panel del formulario
    JLabel jLabelNombre, jLabelCantEmpleados;
    JTextField jTextFieldNombre, jTextFieldCantEmpleados;
    JButton jButtonAgregarTarea, jButtonAsignarEmpleados, jButtonGuardar;
    UtilidadEstilo estilo;


    public FormularioProyecto(PanelManager panel) {
        this.panel = panel;
        armarFormulario();
    }

    public void armarFormulario()
    {
        serviceProyecto = new ServiceProyecto(new DAOProyecto());

        formularioProyecto = new JPanel();
        formularioProyecto.setLayout(new FlowLayout(FlowLayout.CENTER));

        proyecto = new Proyecto();
        try {
            serviceProyecto.guardar(proyecto);
            proyecto.setId(serviceProyecto.ultimoId());
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(null, "no se puede");
            throw new RuntimeException(e);
        }


        jLabelNombre = new JLabel("Nombre");
        jTextFieldNombre = new JTextField(20);

        jLabelCantEmpleados = new JLabel("Empleados Necesarios");
        jTextFieldCantEmpleados = new JTextField(20);

        agregarElementos();

        jButtonAgregarTarea = new JButton("Agregar Tarea");
        jButtonAgregarTarea.addActionListener(e -> {
            try {
                panel = new PanelManager(1, proyecto);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });

        jButtonAsignarEmpleados = new JButton("Asignar Empleados");
        jButtonAsignarEmpleados.addActionListener(e -> {
            try {
                panel = new PanelManager(4, proyecto);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });

        jButtonGuardar = new JButton("Guardar");
        jButtonGuardar.addActionListener(e -> {
            proyecto.setNombre(jTextFieldNombre.getText());
            proyecto.setCantidadEmpleados(Integer.parseInt(jTextFieldCantEmpleados.getText()));
            System.out.println("AAAAAAAAAAAA"+ proyecto);
            try {
                serviceProyecto.modificar(proyecto);
            } catch (ServiceException s) {
                JOptionPane.showMessageDialog(null, "no se puede");
                throw new RuntimeException(s);
            }
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(formularioProyecto);
            jFrame.dispose();
        });

        agregarBotones();

        darEstilo();

        setLayout(new BorderLayout());
        add(formularioProyecto, BorderLayout.CENTER);
    }

    @Override
    public void agregarElementos() {
        JPanel panelTexto = new JPanel(new GridLayout(2, 2));
        panelTexto.add(jLabelNombre);
        panelTexto.add(jTextFieldNombre);
        panelTexto.add(jLabelCantEmpleados);
        panelTexto.add(jTextFieldCantEmpleados);

        formularioProyecto.add(panelTexto);

    }

    public void agregarBotones() {

        JPanel panelBotones = new JPanel(new GridLayout(1, 3));
        panelBotones.add(jButtonAgregarTarea);
        panelBotones.add(jButtonAsignarEmpleados);
        panelBotones.add(jButtonGuardar);

        formularioProyecto.add(panelBotones);
    }

    @Override
    public void darEstilo() {
        estilo = new UtilidadEstilo();
        estilo.estiloJLabel(jLabelNombre);
        estilo.estiloJLabel(jLabelCantEmpleados);
        estilo.estiloJTextField(jTextFieldNombre);
        estilo.estiloJTextField(jTextFieldCantEmpleados);
        estilo.estiloJButton(jButtonAgregarTarea);
        estilo.estiloJButton(jButtonAsignarEmpleados);
        estilo.estiloJButton(jButtonGuardar);
    }


}