package gui.formulario;

import dao.DAOTarea;
import entidades.Empleado;
import entidades.Proyecto;
import entidades.Tarea;
import gui.PanelManager;
import gui.estilos.UtilidadEstilo;
import service.ServiceException;
import service.ServiceTarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
//dependencias tarea, serviceTarea, PanelManager
public class FormularioTarea extends JPanel implements Formulario {
    Proyecto proyecto;
    ServiceTarea serviceTarea;
    PanelManager panel;
    JPanel formularioTarea;
    JLabel jLabelTitulo, jLabelDescripcion, jLabelEstimacionHoras, jLabelEmpleado;
    JTextField jTextFieldTitulo, jTextFieldDescripcion, jTextFieldEstimacionHoras;
    JComboBox<Empleado> jComboBoxempleados; //lista de empleados
    JButton jButtonAgregar;
    UtilidadEstilo estilo;


    public FormularioTarea(PanelManager panel, Proyecto proyecto) throws ServiceException {
        this.panel = panel;
        this.proyecto = proyecto;
        armarFormulario();
    }

    public void armarFormulario() throws ServiceException
    {
        serviceTarea = new ServiceTarea(new DAOTarea());

        formularioTarea = new JPanel();
        formularioTarea.setLayout(new FlowLayout(FlowLayout.CENTER));

        agregarElementos();

        jButtonAgregar = new JButton("Agregar");

        jButtonAgregar.addActionListener(e -> {

            Tarea tarea = new Tarea();
            tarea.setTitulo(jTextFieldTitulo.getText());
            tarea.setDescripcion(jTextFieldDescripcion.getText());
            tarea.setHorasEstimadas(Integer.parseInt(jTextFieldEstimacionHoras.getText()));
            tarea.setProyecto(proyecto);
            proyecto.agregarTarea(tarea);
            try {
                serviceTarea.guardar(tarea);
            } catch (ServiceException s) {
                JOptionPane.showMessageDialog(null, "no se puede");
            }
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(formularioTarea);
            jFrame.dispose();
        });

        agregarBotones();
        darEstilo();

        setLayout(new BorderLayout());
        add(formularioTarea, BorderLayout.CENTER);
    }

    @Override
    public void agregarElementos() {
        jLabelTitulo = new JLabel("Nombre");
        jTextFieldTitulo = new JTextField(20);
        jLabelDescripcion = new JLabel("Descripcion");
        jTextFieldDescripcion = new JTextField(20);
        jLabelEstimacionHoras = new JLabel("Estimacion de horas");
        jTextFieldEstimacionHoras = new JTextField(20);

        JPanel panelTexto = new JPanel(new GridLayout(4,2));
        panelTexto.add(jLabelTitulo);
        panelTexto.add(jTextFieldTitulo);
        panelTexto.add(jLabelDescripcion);
        panelTexto.add(jTextFieldDescripcion);
        panelTexto.add(jLabelEstimacionHoras);
        panelTexto.add(jTextFieldEstimacionHoras);

        formularioTarea.add(panelTexto);
    }

    @Override
    public void agregarBotones() {
        JPanel panelBotones = new JPanel(new GridLayout(1,1));
        panelBotones.add(jButtonAgregar);

        formularioTarea.add(panelBotones);
    }

    @Override
    public void darEstilo() {
        estilo = new UtilidadEstilo();
        estilo.estiloJLabel(jLabelTitulo);
        estilo.estiloJLabel(jLabelDescripcion);
        estilo.estiloJLabel(jLabelEstimacionHoras);
        estilo.estiloJTextField(jTextFieldTitulo);
        estilo.estiloJTextField(jTextFieldDescripcion);
        estilo.estiloJTextField(jTextFieldEstimacionHoras);
        estilo.estiloJButton(jButtonAgregar);
    }
}
