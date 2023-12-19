package gui.formulario;

import dao.DAOEmpleado;
import entidades.Empleado;
import gui.PanelManager;
import gui.estilos.UtilidadEstilo;
import service.ServiceEmpleado;

import javax.swing.*;
import java.awt.*;

public class FormularioEmpleado extends JPanel implements Formulario {
    ServiceEmpleado serviceEmpleado;
    PanelManager panel;
    JPanel formularioEmpleado;
    JLabel jLabelNombre, jLabelCostoHora;
    JTextField jTextFieldNombre, jTextFieldCostoHora;
    JButton jButtonGuardar;
    UtilidadEstilo estilo;

    public FormularioEmpleado(PanelManager panel) {
        this.panel = panel;
        armarFormulario();
    }

    public void armarFormulario()
    {
        serviceEmpleado = new ServiceEmpleado(new DAOEmpleado());

        formularioEmpleado = new JPanel();
        formularioEmpleado.setLayout(new FlowLayout(FlowLayout.CENTER));

        agregarElementos();

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

        agregarBotones();
        darEstilo();

        setLayout(new BorderLayout());
        add(formularioEmpleado, BorderLayout.CENTER);
    }

    @Override
    public void agregarElementos() {
        jLabelNombre = new JLabel("Nombre");
        jTextFieldNombre = new JTextField(20);
        jLabelCostoHora = new JLabel("Costo por Hora");
        jTextFieldCostoHora = new JTextField(20);

        JPanel panelTexto = new JPanel(new GridLayout(2,2));
        panelTexto.add(jLabelNombre);
        panelTexto.add(jTextFieldNombre);
        panelTexto.add(jLabelCostoHora);
        panelTexto.add(jTextFieldCostoHora);

        formularioEmpleado.add(panelTexto);

    }

    @Override
    public void agregarBotones() {
        JPanel botones = new JPanel(new GridLayout(1,1));
        botones.add(jButtonGuardar);

        formularioEmpleado.add(botones);
    }

    @Override
    public void darEstilo() {
        estilo = new UtilidadEstilo();
        estilo.estiloJLabel(jLabelNombre);
        estilo.estiloJLabel(jLabelCostoHora);
        estilo.estiloJTextField(jTextFieldNombre);
        estilo.estiloJTextField(jTextFieldCostoHora);
    }
}
