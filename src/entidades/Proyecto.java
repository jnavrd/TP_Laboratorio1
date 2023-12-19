package entidades;

import dao.DAOHistorialTrabajo;
import dao.DAOTarea;
import service.ServiceException;
import service.ServiceHistorialTrabajo;
import service.ServiceTarea;

import java.util.ArrayList;

public class Proyecto {
    private ServiceTarea serviceTarea;
    private ServiceHistorialTrabajo serviceHistorialTrabajo;
    private int id;
    private String nombre;
    private int cantidadEmpleados; //cuantos empleados se necesitan para el proyecto necesario??
    private ArrayList<Tarea> tareas;
    private ArrayList<Empleado> empleados;
    private int estado; //-1 por no empezado, 0 en curso, 1 terminado
    ArrayList<HistorialTrabajo> historialProyecto;


    //constructores
    public Proyecto() {
        this.estado = -1;
        tareas = new ArrayList<>();
        empleados = new ArrayList<>();
    }
    public Proyecto(int id, String nombre) {
        this.nombre = nombre;
        this.estado = -1;
        tareas = new ArrayList<>();
        empleados = new ArrayList<>();
    }
    //----------------------------fin constructores------------------------------------------

    //metodos
    public void agregarEmpleado(Empleado empleado){
        empleados.add(empleado);
    }
    public void eliminarEmpleado(Empleado empleado){
        empleados.remove(empleado);
    }
    public void agregarTarea(Tarea tarea){
        tareas.add(tarea);
    }
    public void eliminarTarea(Tarea tarea){
        tareas.remove(tarea);
    }

    public void setearTareas() throws ServiceException {
        serviceTarea = new ServiceTarea(new DAOTarea());
        tareas = serviceTarea.buscarEnProyecto(this.getId());
    }

    public int calcularHorasEstimadas() {
        int horasEstimadas = 0;
        for(Tarea tarea: tareas){
            horasEstimadas += tarea.getHorasEstimadas();
        }
        return horasEstimadas;
    }

    public int calcularHorasTotales() {
        int horasTotales = 0;
        for(Tarea tarea: tareas){
            horasTotales += tarea.getHorasReales();
        }
        return horasTotales;
    }

    public void actualizarEstadoIniciar() {
        for(Tarea tarea: tareas){
            if(tarea.getEstado() == 0)
            {
                this.estado = 0;
            }
        }
    }

    public void actualizarEstadoTerminar() {
        boolean tareasTerminadas = false;
        for(Tarea tarea: tareas){
            if(tarea.getEstado() == 2)
            {
                tareasTerminadas = true;
            }
        }
        if(tareasTerminadas) {
            this.estado = 2;
        }
    }

    //TODO: calcular costo
    public int calcularCosto() throws ServiceException {

        serviceHistorialTrabajo = new ServiceHistorialTrabajo(new DAOHistorialTrabajo());
        int costo = 0;

        historialProyecto = new ArrayList<>();
        try {
            historialProyecto = serviceHistorialTrabajo.buscarEnProyecto(this.getId());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        for (HistorialTrabajo historial : historialProyecto) {
            int horasTrabajadas = historial.getHorasTrabajadas();
            costo += horasTrabajadas * historial.getEmpleado().getCostoHora();
        }
        return costo;
    }

    //-----------------------------fin metodos-----------------------------------------

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadEmpleados() {
        return cantidadEmpleados;
    }

    public void setCantidadEmpleados(int cantidadEmpleados) {
        this.cantidadEmpleados = cantidadEmpleados;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    //-----------------------------fin getters y setters-------------------------------------

    @Override
    public String toString() {
        return  nombre + '\'' +
                ", estado=" + estado;
    }

    /*@Override
    public String toString() {
        return "Proyecto{" +
                ", nombre='" + nombre + '\'' +
                ", cantidadEmpleados=" + cantidadEmpleados +
                ", tareas=" + tareas +
                ", estado=" + estado +
                '}';
    }*/
}
