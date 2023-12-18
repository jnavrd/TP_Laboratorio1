package gui.detalle;

import dao.DAOEmpleado;
import dao.DAOHistorialTrabajo;
import dao.DAOProyecto;
import dao.DAOTarea;
import entidades.Empleado;
import entidades.HistorialTrabajo;
import entidades.Proyecto;
import entidades.Tarea;
import gui.PanelManager;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//dependencias: Tarea, Empleado, HistorialTrabajo, ServiceTarea
public class DetalleTarea extends JPanel {
    ServiceTarea serviceTarea;
    ServiceProyecto serviceProyecto;
    ServiceEmpleado serviceEmpleado;
    ServiceHistorialTrabajo serviceHistorialTrabajo;
    PanelManager panel;
    HistorialTrabajo historialTrabajo;
    JPanel detalleTarea;
    JDialog jDialog;
    JLabel jLabelTitulo, jLabelDescripcion, jLabelEstimacionHoras, jLabelEmpleado;
    JTextField jTextFieldTitulo, jTextFieldDescripcion, jTextFieldEstimacionHoras, jTextFieldEmpleado;
    JComboBox<Empleado> jComboBoxEmpleados;
    JButton jButtonIniciarTarea, jButtonPausarTarea, jButtonFinalizarTarea, jButtonEditar, jButtonGuardar, jButtonEliminar;
    //temporal
    ArrayList<Empleado> empleados = new ArrayList<>();
    public DetalleTarea(Tarea tarea) {
        this.panel = panel;
        armarTarea(tarea);
    }

    public void armarTarea(Tarea tarea)
    {
        serviceTarea = new ServiceTarea(new DAOTarea());
        serviceEmpleado = new ServiceEmpleado(new DAOEmpleado());
        serviceProyecto = new ServiceProyecto(new DAOProyecto());

        detalleTarea = new JPanel();
        detalleTarea.setLayout(new BoxLayout(detalleTarea, BoxLayout.Y_AXIS));

        jLabelTitulo = new JLabel("Titulo:");
        jLabelDescripcion = new JLabel("Descripcion:");
        jLabelEstimacionHoras = new JLabel("Horas estimadas:");
        jLabelEmpleado = new JLabel("Empleado:");

        jButtonIniciarTarea = new JButton("Iniciar");
        jButtonPausarTarea = new JButton("Pausar");
        jButtonFinalizarTarea = new JButton("Finalizar");

        JPanel jPanelBotonesArriba = new JPanel(); //botones de iniciar, pausar, finalizar
        jPanelBotonesArriba = new JPanel(new GridLayout(3,1));

        jPanelBotonesArriba.add(jButtonIniciarTarea);
        jPanelBotonesArriba.add(jButtonPausarTarea);
        jPanelBotonesArriba.add(jButtonFinalizarTarea);

        jButtonIniciarTarea.addActionListener(e -> {
            try {
                iniciarTarea(tarea);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
        });
        if(tarea.getEstado() == 0)
        {
            jButtonPausarTarea.addActionListener(e -> pausarTarea(tarea));
            jButtonFinalizarTarea.addActionListener(e -> terminarTarea(tarea));
        }

        jTextFieldTitulo = new JTextField(tarea.getTitulo());
        jTextFieldTitulo.setEditable(false);
        jTextFieldDescripcion = new JTextField(tarea.getDescripcion());
        jTextFieldDescripcion.setEditable(false);
        jTextFieldEstimacionHoras = new JTextField(String.valueOf(tarea.getHorasEstimadas()));
        jTextFieldEstimacionHoras.setEditable(false);
        if(tarea.getEmpleado() == null) {
            jTextFieldEmpleado = new JTextField("No asignado");
        }
        else {
            jTextFieldEmpleado = new JTextField(tarea.getEmpleado().getNombre());
        }
        jTextFieldEmpleado.setEditable(false);


        JPanel jPanelBotonesAbajo = new JPanel(new GridLayout(1,3)); //botones de editar, guardar y eliminar

        jButtonEditar = new JButton("Editar");
        jButtonEditar.addActionListener(e -> {
            //hacer que los campos sean editables
            jTextFieldTitulo.setEditable(true);
            jTextFieldDescripcion.setEditable(true);
            jTextFieldEstimacionHoras.setEditable(true);

            //si no existe el boton guardar, crearlo
            if (jButtonGuardar == null) {
                jButtonGuardar = new JButton("Guardar");
                jPanelBotonesAbajo.add(jButtonGuardar);
                detalleTarea.revalidate();
            }

            jButtonGuardar.addActionListener(e1 -> {

                //guardar los cambios
                tarea.setTitulo(jTextFieldTitulo.getText());
                tarea.setDescripcion(jTextFieldDescripcion.getText());
                tarea.setHorasEstimadas(Integer.parseInt(jTextFieldEstimacionHoras.getText()));

                try {
                    serviceTarea.modificar(tarea);
                } catch (ServiceException se) {
                    JOptionPane.showMessageDialog(null, "no se puede");
                }

                jTextFieldTitulo.setEditable(false);
                jTextFieldDescripcion.setEditable(false);
                jTextFieldEstimacionHoras.setEditable(false);
                jButtonGuardar.setVisible(false);
                jButtonGuardar = null;
                detalleTarea.revalidate();
                detalleTarea.repaint();
            });
        });

        jButtonEliminar = new JButton("Eliminar");
        jButtonEliminar.addActionListener(e -> {
            try {
                serviceTarea.eliminar(tarea.getId());
            } catch (ServiceException se) {
                JOptionPane.showMessageDialog(null, "no se puede");
            }
            JFrame jFrame = (JFrame) SwingUtilities.getWindowAncestor(detalleTarea);
            jFrame.dispose();
        });

        jPanelBotonesAbajo.add(jButtonEditar);
        jPanelBotonesAbajo.add(jButtonEliminar);

        detalleTarea.add(jPanelBotonesArriba, BorderLayout.EAST);
        detalleTarea.add(jLabelTitulo);
        detalleTarea.add(jTextFieldTitulo);
        detalleTarea.add(jLabelDescripcion);
        detalleTarea.add(jTextFieldDescripcion);
        detalleTarea.add(jLabelEstimacionHoras);
        detalleTarea.add(jTextFieldEstimacionHoras);
        detalleTarea.add(jLabelEmpleado);
        detalleTarea.add(jTextFieldEmpleado);
        detalleTarea.add(jPanelBotonesAbajo, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(detalleTarea, BorderLayout.CENTER);
    }


    public void iniciarTarea(Tarea tarea) throws ServiceException {
        //elegir quien esta por iniciar la tarea
        //asignar empleado a tarea
        //cambiar estado a 0
        jDialog = crearJDialog();

        try {
            empleados = serviceEmpleado.buscarLibres();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        jComboBoxEmpleados = new JComboBox<>();
        for (Empleado empleado : empleados) {

            if (empleado.getProyecto() != null && empleado.getProyecto().getId() == tarea.getProyecto().getId())
                jComboBoxEmpleados.addItem(empleado);
        }

        JButton jButtonAceptar = new JButton("Aceptar");
        jButtonAceptar.addActionListener(e -> {
            Empleado empleadoSeleccionado = (Empleado) jComboBoxEmpleados.getSelectedItem();
            tarea.setEmpleado(empleadoSeleccionado);
            tarea.setEstado(0);
            tarea.getProyecto().actualizarEstado();
            try {
                serviceTarea.modificar(tarea);
                serviceTarea.modificarEmpleado(tarea);
                serviceProyecto.modificar(tarea.getProyecto());
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            jDialog.dispose();
            jTextFieldEmpleado.setText(empleadoSeleccionado.getNombre());
        });

        jDialog.getContentPane().add(jComboBoxEmpleados);
        jDialog.getContentPane().add(jButtonAceptar);

        jDialog.setVisible(true);
    }


    public void pausarTarea(Tarea tarea) {
        //preguntar cant de horas trabajadas
        //sumar a horas reales
        //cambiar estado a 1
        jDialog = crearJDialog();

        JLabel jLabelHorasTrabajadas = new JLabel("Horas trabajadas");
        JTextField jTextFieldHorasTrabajadas = new JTextField();
        JButton jButtonAceptar = new JButton("Aceptar");

        jButtonAceptar.addActionListener(e -> {
            int horasReales = tarea.getHorasReales();
            horasReales += Integer.parseInt(jTextFieldHorasTrabajadas.getText());

            tarea.setHorasReales(horasReales);
            tarea.setEstado(1);

            agregarAlHistorial(tarea.getProyecto(), tarea.getEmpleado(), tarea, Integer.parseInt(jTextFieldHorasTrabajadas.getText()));

            tarea.setEmpleado(null);

            try {
                serviceTarea.modificar(tarea);
                serviceTarea.modificarEmpleado(tarea);
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            jDialog.dispose();
        });
        jDialog.getContentPane().add(jLabelHorasTrabajadas);
        jDialog.getContentPane().add(jTextFieldHorasTrabajadas);
        jDialog.getContentPane().add(jButtonAceptar);

        jDialog.setVisible(true);
    }

    public void terminarTarea(Tarea tarea) {
        //cambiar estado a 2
        //calcular horas reales
        //calcular costo
        jDialog = crearJDialog();

        JLabel jLabelHorasTrabajadas = new JLabel("Horas trabajadas");
        JTextField jTextFieldHorasTrabajadas = new JTextField();
        JButton jButtonAceptar = new JButton("Aceptar");

        jButtonAceptar.addActionListener(e -> {
            int horasReales = tarea.getHorasReales();
            horasReales += Integer.parseInt(jTextFieldHorasTrabajadas.getText());

            tarea.setHorasReales(horasReales);
            tarea.setEstado(2);
            tarea.getProyecto().actualizarEstado();

            agregarAlHistorial(tarea.getProyecto(), tarea.getEmpleado(), tarea, Integer.parseInt(jTextFieldHorasTrabajadas.getText()));

            tarea.setEmpleado(null);
            
            try {
                serviceTarea.modificar(tarea);
                serviceProyecto.modificar(tarea.getProyecto());
            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }
            jDialog.dispose();
        });
        jDialog.getContentPane().add(jLabelHorasTrabajadas);
        jDialog.getContentPane().add(jTextFieldHorasTrabajadas);
        jDialog.getContentPane().add(jButtonAceptar);

        jDialog.setVisible(true);

    }

    public void agregarAlHistorial(Proyecto proyecto, Empleado empleado, Tarea tarea, int horasTrabajadas) {
        historialTrabajo = new HistorialTrabajo(proyecto, empleado, tarea, horasTrabajadas);
        serviceHistorialTrabajo = new ServiceHistorialTrabajo(new DAOHistorialTrabajo());
        try {
            serviceHistorialTrabajo.guardar(historialTrabajo);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public JDialog crearJDialog()
    {
        JDialog jDialog = new JDialog();
        jDialog.setLayout(new GridLayout(2,1));
        jDialog.setModal(true);
        jDialog.setSize(300, 200);
        jDialog.setLocationRelativeTo(null);

        return jDialog;
    }
}
