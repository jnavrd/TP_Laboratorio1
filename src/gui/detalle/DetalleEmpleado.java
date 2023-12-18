package gui.detalle;

import dao.DAOEmpleado;
import dao.DAOHistorialTrabajo;
import dao.DAOProyecto;
import dao.DAOTarea;
import entidades.Empleado;
import entidades.Proyecto;
import entidades.Tarea;
import gui.PanelManager;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DetalleEmpleado extends JPanel {
    ServiceEmpleado serviceEmpleado;
    ServiceProyecto serviceProyecto;
    ServiceTarea serviceTarea;
    ServiceHistorialTrabajo serviceHistorialTrabajo;
    PanelManager panel;
    JPanel detalleEmpleado;
    JLabel jLabelNombre, jLabelCostoHora, jLabelProyecto, jLabelTarea, jLabelEstado, JLabelHorasTrabajadas;
    JTextField jTextFieldNombre, jTextFieldCostoHora, jTextFieldProyecto, jTextFieldTarea, jTextFieldEstado, jTextFieldHorasTrabajadas;
    JButton jButtonEditar, jButtonGuardar, jButtonEliminar;
    int horasTrabajadas;
    ArrayList<Proyecto> proyectos = new ArrayList<>();
    JComboBox<Proyecto> jComboBoxProyectos;

    public DetalleEmpleado(Empleado empleado) throws ServiceException {
        this.panel = panel;
        armarEmpelado(empleado);
    }

    public void armarEmpelado(Empleado empleado) throws ServiceException {
        serviceEmpleado = new ServiceEmpleado(new DAOEmpleado());
        serviceProyecto = new ServiceProyecto(new DAOProyecto());
        serviceTarea = new ServiceTarea(new DAOTarea());
        serviceHistorialTrabajo = new ServiceHistorialTrabajo(new DAOHistorialTrabajo());

        detalleEmpleado = new JPanel();
        detalleEmpleado.setLayout(new BoxLayout(detalleEmpleado, BoxLayout.Y_AXIS));

        jLabelNombre = new JLabel("Nombre");
        jLabelCostoHora = new JLabel("Costo por hora");
        jLabelProyecto = new JLabel("Proyecto");
        jLabelTarea = new JLabel("Tarea");
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
        if(empleado.getTarea() == null)
            jTextFieldTarea = new JTextField("No asignado");
        else
            jTextFieldTarea = new JTextField(empleado.getTarea().getTitulo());
        jTextFieldTarea.setEditable(false);
        String estado = "";
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

        JPanel jPanelBotones = new JPanel(new GridLayout(1,3));

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

            detalleEmpleado.add(jComboBoxProyectos, index);
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
                    detalleEmpleado.remove(jComboBoxProyectos);
                    detalleEmpleado.add(jTextFieldProyecto, index);
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

        jPanelBotones.add(jButtonEditar);
        jPanelBotones.add(jButtonEliminar);

        detalleEmpleado.add(jLabelNombre);
        detalleEmpleado.add(jTextFieldNombre);
        detalleEmpleado.add(jLabelCostoHora);
        detalleEmpleado.add(jTextFieldCostoHora);
        detalleEmpleado.add(jLabelProyecto);
        detalleEmpleado.add(jTextFieldProyecto);
        detalleEmpleado.add(jLabelTarea);
        detalleEmpleado.add(jTextFieldTarea);
        detalleEmpleado.add(jLabelEstado);
        detalleEmpleado.add(jTextFieldEstado);
        detalleEmpleado.add(JLabelHorasTrabajadas);
        detalleEmpleado.add(jTextFieldHorasTrabajadas);
        detalleEmpleado.add(jPanelBotones, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(detalleEmpleado, BorderLayout.CENTER);
    }
}
