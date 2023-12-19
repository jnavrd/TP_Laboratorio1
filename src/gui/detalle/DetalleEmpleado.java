package gui.detalle;

import dao.DAOEmpleado;
import dao.DAOHistorialTrabajo;
import dao.DAOProyecto;
import dao.DAOTarea;
import entidades.Empleado;
import entidades.Proyecto;
import entidades.Tarea;
import gui.PanelManager;
import gui.estilos.UtilidadEstilo;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DetalleEmpleado extends JPanel implements Detalle{
    ServiceEmpleado serviceEmpleado;
    ServiceProyecto serviceProyecto;
    ServiceTarea serviceTarea;
    ServiceHistorialTrabajo serviceHistorialTrabajo;
    Empleado empleado;
    PanelManager panel;
    JPanel detalleEmpleado, panelTexto, jPanelBotones;
    JLabel jLabelNombre, jLabelCostoHora, jLabelProyecto, jLabelEstado, JLabelHorasTrabajadas;
    JTextField jTextFieldNombre, jTextFieldCostoHora, jTextFieldProyecto, jTextFieldEstado, jTextFieldHorasTrabajadas;
    JButton jButtonEditar, jButtonGuardar, jButtonEliminar;
    String estado;
    int horasTrabajadas;
    ArrayList<Proyecto> proyectos = new ArrayList<>();
    JComboBox<Proyecto> jComboBoxProyectos;
    UtilidadEstilo estilo;

    public DetalleEmpleado(Empleado empleado) throws ServiceException {
        this.panel = panel;
        this.empleado = empleado;
        armarEmpelado();
    }

    public void armarEmpelado() {
        serviceEmpleado = new ServiceEmpleado(new DAOEmpleado());
        serviceProyecto = new ServiceProyecto(new DAOProyecto());
        serviceTarea = new ServiceTarea(new DAOTarea());
        serviceHistorialTrabajo = new ServiceHistorialTrabajo(new DAOHistorialTrabajo());

        detalleEmpleado = new JPanel();
        detalleEmpleado.setLayout(new BoxLayout(detalleEmpleado, BoxLayout.Y_AXIS));

        agregarElementos();

        jPanelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));

        jButtonEditar = new JButton("Editar");
        jButtonEditar.addActionListener(e -> {
            jTextFieldCostoHora.setEditable(true);
            Container parent = jTextFieldProyecto.getParent();
            int index = parent.getComponentZOrder(jTextFieldProyecto);
            parent.remove(jTextFieldProyecto);

            try {
                proyectos = serviceProyecto.buscarTodos();
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            jComboBoxProyectos = new JComboBox<>();
            for (Proyecto proyecto: proyectos) {
                jComboBoxProyectos.addItem(proyecto);
            }

            panelTexto.add(jComboBoxProyectos, index);
            estilo.estiloJComboBox(jComboBoxProyectos);
            detalleEmpleado.revalidate();
            detalleEmpleado.repaint();

            if (jButtonGuardar == null) {
                jButtonGuardar = new JButton("Guardar");
                jPanelBotones.add(jButtonGuardar);
                detalleEmpleado.revalidate();
            }

            jButtonGuardar.addActionListener(e1 -> {
                try {
                    empleado.setCostoHora(Integer.parseInt(jTextFieldCostoHora.getText()));
                    empleado.setProyecto((Proyecto) jComboBoxProyectos.getSelectedItem());
                    serviceEmpleado.modificar(empleado);
                    serviceProyecto.modificar(empleado.getProyecto());

                    jTextFieldCostoHora.setEditable(false);
                    panelTexto.remove(jComboBoxProyectos);
                    panelTexto.add(jTextFieldProyecto, index);
                    detalleEmpleado.revalidate();
                    detalleEmpleado.repaint();
                } catch (ServiceException ex) {
                    throw new RuntimeException(ex);
                }
            });
        });

        jButtonEliminar = new JButton("Eliminar");
        jButtonEliminar.addActionListener(e -> {
            try {
                Tarea tarea = empleado.getTarea();
                if (tarea != null){
                    tarea.setEmpleado(null);
                    serviceTarea.modificar(tarea);
                }
                serviceEmpleado.eliminar(empleado.getLegajo());
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(detalleEmpleado);
            jFrame.dispose();
        });

        agregarBotones();
        darEstilo();

        setLayout(new BorderLayout());
        add(detalleEmpleado, BorderLayout.CENTER);
    }

    @Override
    public void agregarElementos() {
        jLabelNombre = new JLabel("Nombre");
        jLabelCostoHora = new JLabel("Costo por hora");
        jLabelProyecto = new JLabel("Proyecto");
        jLabelEstado = new JLabel("Estado");
        JLabelHorasTrabajadas = new JLabel("Horas trabajadas");

        jTextFieldNombre = new JTextField(empleado.getNombre());
        jTextFieldNombre.setEditable(false);
        jTextFieldCostoHora = new JTextField(String.valueOf(empleado.getCostoHora()));
        jTextFieldCostoHora.setEditable(false);
        if(empleado.getProyecto() == null)
            jTextFieldProyecto = new JTextField("No asignado");
        else
            jTextFieldProyecto = new JTextField(empleado.getProyecto().getNombre());
        jTextFieldProyecto.setEditable(false);

        if (empleado.isLibre())
            estado = "Libre";
        else
            estado = "Asignado";
        jTextFieldEstado = new JTextField(estado);
        jTextFieldEstado.setEditable(false);
        try{
            horasTrabajadas = serviceHistorialTrabajo.horasTrabajadasTotal(empleado.getLegajo());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        jTextFieldHorasTrabajadas = new JTextField(String.valueOf(horasTrabajadas));
        jTextFieldHorasTrabajadas.setEditable(false);

        panelTexto = new JPanel(new GridLayout(5,2));
        panelTexto.add(jLabelNombre);
        panelTexto.add(jTextFieldNombre);
        panelTexto.add(jLabelCostoHora);
        panelTexto.add(jTextFieldCostoHora);
        panelTexto.add(jLabelProyecto);
        panelTexto.add(jTextFieldProyecto);
        panelTexto.add(jLabelEstado);
        panelTexto.add(jTextFieldEstado);
        panelTexto.add(JLabelHorasTrabajadas);
        panelTexto.add(jTextFieldHorasTrabajadas);

        panelTexto.setMaximumSize(new Dimension(panelTexto.getMaximumSize().width, Integer.MAX_VALUE));

        detalleEmpleado.add(panelTexto, BorderLayout.CENTER);
    }

    public void agregarBotones() {
        jPanelBotones.add(jButtonEditar);
        jPanelBotones.add(jButtonEliminar);

        detalleEmpleado.add(jPanelBotones, BorderLayout.SOUTH);
    }

    @Override
    public void darEstilo() {
        estilo = new UtilidadEstilo();
        estilo.estiloJLabel(jLabelNombre);
        estilo.estiloJLabel(jLabelCostoHora);
        estilo.estiloJLabel(jLabelProyecto);
        estilo.estiloJLabel(jLabelEstado);
        estilo.estiloJLabel(JLabelHorasTrabajadas);
        estilo.estiloJTextField(jTextFieldNombre);
        estilo.estiloJTextField(jTextFieldCostoHora);
        estilo.estiloJTextField(jTextFieldProyecto);
        estilo.estiloJTextField(jTextFieldEstado);
        estilo.estiloJTextField(jTextFieldHorasTrabajadas);
        estilo.estiloJButton(jButtonEditar);
    }
}
