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
import gui.estilos.UtilidadEstilo;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//dependencias: Tarea, Empleado, HistorialTrabajo, ServiceTarea
public class DetalleTarea extends JPanel implements Detalle {
    ServiceTarea serviceTarea;
    ServiceProyecto serviceProyecto;
    ServiceEmpleado serviceEmpleado;
    ServiceHistorialTrabajo serviceHistorialTrabajo;
    Tarea tarea;
    PanelManager panel;
    HistorialTrabajo historialTrabajo;
    JPanel detalleTarea;
    JDialog jDialog;
    JLabel jLabelTitulo, jLabelDescripcion, jLabelEstimacionHoras, jLabelEmpleado;
    JTextField jTextFieldTitulo, jTextFieldDescripcion, jTextFieldEstimacionHoras, jTextFieldEmpleado;
    JComboBox<Empleado> jComboBoxEmpleados;
    JButton jButtonIniciarTarea, jButtonPausarTarea, jButtonFinalizarTarea, jButtonEditar, jButtonGuardar, jButtonEliminar;
    ArrayList<Empleado> empleados = new ArrayList<>();
    UtilidadEstilo estilo;
    public DetalleTarea(Tarea tarea) {
        this.panel = panel;
        this.tarea = tarea;
        armarTarea();
    }

    public void armarTarea()
    {
        serviceTarea = new ServiceTarea(new DAOTarea());
        serviceEmpleado = new ServiceEmpleado(new DAOEmpleado());
        serviceProyecto = new ServiceProyecto(new DAOProyecto());


        detalleTarea = new JPanel();
        detalleTarea.setLayout(new BoxLayout(detalleTarea, BoxLayout.Y_AXIS));

        jButtonIniciarTarea = new JButton("Iniciar");
        jButtonPausarTarea = new JButton("Pausar");
        jButtonFinalizarTarea = new JButton("Finalizar");

        JPanel jPanelBotonesArriba = new JPanel(); //botones de iniciar, pausar, finalizar
        jPanelBotonesArriba = new JPanel(new FlowLayout(FlowLayout.CENTER));

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

        JPanel jPanelBotonesAbajo = new JPanel(new FlowLayout(FlowLayout.CENTER)); //botones de editar, guardar y eliminar

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

        detalleTarea.add(jPanelBotonesArriba, BorderLayout.NORTH);
        agregarElementos();
        detalleTarea.add(jPanelBotonesAbajo, BorderLayout.CENTER);

        darEstilo();

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
            tarea.getProyecto().actualizarEstadoIniciar();
            tarea.getEmpleado().setLibre(false);
            try {
                serviceTarea.modificar(tarea);
                serviceTarea.modificarEmpleado(tarea);
                serviceProyecto.modificar(tarea.getProyecto());
                serviceEmpleado.modificar(tarea.getEmpleado());
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
            tarea.getEmpleado().setLibre(true);

            try {
                agregarAlHistorial(tarea.getProyecto(), tarea.getEmpleado(), tarea, Integer.parseInt(jTextFieldHorasTrabajadas.getText()));
                serviceEmpleado.modificar(tarea.getEmpleado());
                serviceTarea.modificar(tarea);

                tarea.setEmpleado(null);

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
            tarea.getEmpleado().setLibre(true);
            tarea.getProyecto().actualizarEstadoTerminar();

            agregarAlHistorial(tarea.getProyecto(), tarea.getEmpleado(), tarea, Integer.parseInt(jTextFieldHorasTrabajadas.getText()));

            try {
                serviceTarea.modificar(tarea);
                serviceProyecto.modificar(tarea.getProyecto());
                serviceEmpleado.modificar(tarea.getEmpleado());

            } catch (ServiceException ex) {
                throw new RuntimeException(ex);
            }

            tarea.setEmpleado(null);

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

    @Override
    public void agregarElementos() {
        jLabelTitulo = new JLabel("Titulo:");
        jLabelDescripcion = new JLabel("Descripcion:");
        jLabelEstimacionHoras = new JLabel("Horas estimadas:");
        jLabelEmpleado = new JLabel("Empleado:");

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

        JPanel panelTexto = new JPanel(new GridLayout(4,2));
        panelTexto.add(jLabelTitulo);
        panelTexto.add(jTextFieldTitulo);
        panelTexto.add(jLabelDescripcion);
        panelTexto.add(jTextFieldDescripcion);
        panelTexto.add(jLabelEstimacionHoras);
        panelTexto.add(jTextFieldEstimacionHoras);
        panelTexto.add(jLabelEmpleado);
        panelTexto.add(jTextFieldEmpleado);

        panelTexto.setMaximumSize(new Dimension(panelTexto.getMaximumSize().width, Integer.MAX_VALUE));

        detalleTarea.add(panelTexto, BorderLayout.CENTER);
    }

    @Override
    public void darEstilo() {
        estilo = new UtilidadEstilo();
        estilo.estiloJLabel(jLabelTitulo);
        estilo.estiloJLabel(jLabelDescripcion);
        estilo.estiloJLabel(jLabelEstimacionHoras);
        estilo.estiloJLabel(jLabelEmpleado);
        estilo.estiloJTextField(jTextFieldTitulo);
        estilo.estiloJTextField(jTextFieldDescripcion);
        estilo.estiloJTextField(jTextFieldEstimacionHoras);
        estilo.estiloJTextField(jTextFieldEmpleado);
        estilo.estiloJButton(jButtonIniciarTarea);
        estilo.estiloJButton(jButtonPausarTarea);
        estilo.estiloJButton(jButtonFinalizarTarea);
    }
}
