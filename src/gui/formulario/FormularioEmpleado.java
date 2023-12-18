package gui.formulario;

import dao.DAOEmpleado;
import entidades.Empleado;
import gui.PanelManager;
import service.ServiceEmpleado;

import javax.swing.*;
import java.awt.*;

public class FormularioEmpleado extends JPanel {
    ServiceEmpleado serviceEmpleado;
    PanelManager panel;
    JPanel formularioEmpleado;
    JLabel nombre;
    JLabel costoHora;
    JTextField jTextFieldNombre;
    JTextField jTextFieldCostoHora;
    JButton jButtonGuardar;

    public FormularioEmpleado(PanelManager panel) {
        this.panel = panel;
        armarFormulario();
    }

    public void armarFormulario()
    {
        serviceEmpleado = new ServiceEmpleado(new DAOEmpleado());

        formularioEmpleado = new JPanel();
        formularioEmpleado.setLayout(new GridLayout(3,1));

        nombre = new JLabel("Nombre");
        jTextFieldNombre = new JTextField(20);

        costoHora = new JLabel("Costo por Hora");
        jTextFieldCostoHora = new JTextField(20);

        jButtonGuardar = new JButton("Guardar");
        jButtonGuardar.addActionListener(e -> {
            Empleado empleado = new Empleado();
            empleado.setNombre(jTextFieldNombre.getText());
            empleado.setCostoHora(Integer.parseInt((jTextFieldCostoHora.getText())));
            try {
                serviceEmpleado.guardar(empleado);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No se pudo guardar el empleado");
                throw new RuntimeException(ex);
            }
        });

        formularioEmpleado.add(nombre);
        formularioEmpleado.add(jTextFieldNombre);
        formularioEmpleado.add(costoHora);
        formularioEmpleado.add(jTextFieldCostoHora);
        formularioEmpleado.add(jButtonGuardar);

        setLayout(new BorderLayout());
        add(formularioEmpleado, BorderLayout.CENTER);
    }
}
