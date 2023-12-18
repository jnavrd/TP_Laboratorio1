package gui.formulario;

import dao.DAOTarea;
import entidades.Empleado;
import entidades.Proyecto;
import entidades.Tarea;
import gui.PanelManager;
import service.ServiceException;
import service.ServiceTarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
//dependencias tarea, serviceTarea, PanelManager
public class FormularioTarea extends JPanel {
    Proyecto proyecto;
    ServiceTarea serviceTarea;
    PanelManager panel;
    JPanel formularioTarea;
    JLabel jLabelTitulo, jLabelDescripcion, jLabelEstimacionHoras, jLabelEmpleado;
    JTextField jTextFieldTitulo, jTextFieldDescripcion, jTextFieldEstimacionHoras;
    JComboBox<Empleado> jComboBoxempleados; //lista de empleados
    JButton jButtonAgregar;


    public FormularioTarea(PanelManager panel, Proyecto proyecto) throws ServiceException {
        this.panel = panel;
        this.proyecto = proyecto;
        armarFormulario();
    }

    public void armarFormulario() throws ServiceException
    {
        serviceTarea = new ServiceTarea(new DAOTarea());

        formularioTarea = new JPanel();
        formularioTarea.setLayout(new GridLayout(5,1));

        jLabelTitulo = new JLabel("Nombre");
        jTextFieldTitulo = new JTextField(20);

        jLabelDescripcion = new JLabel("Descripcion");
        jTextFieldDescripcion = new JTextField(20);

        jLabelEstimacionHoras = new JLabel("Estimacion de horas");
        jTextFieldEstimacionHoras = new JTextField(20);

        jLabelEmpleado = new JLabel("Empleado");
        jComboBoxempleados = new JComboBox<>();

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

        formularioTarea.add(jLabelTitulo);
        formularioTarea.add(jTextFieldTitulo);
        formularioTarea.add(jLabelDescripcion);
        formularioTarea.add(jTextFieldDescripcion);
        formularioTarea.add(jLabelEstimacionHoras);
        formularioTarea.add(jTextFieldEstimacionHoras);
        formularioTarea.add(jButtonAgregar);

        setLayout(new BorderLayout());
        add(formularioTarea, BorderLayout.CENTER);
    }
}
