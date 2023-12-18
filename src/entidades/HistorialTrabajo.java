package entidades;


import java.util.ArrayList;

public class HistorialTrabajo {
    private Proyecto proyecto;
    private Empleado empleado;
    private Tarea tarea;
    private int horasTrabajadas;
    private ArrayList<HistorialTrabajo> historialTrabajoLista = new ArrayList<>();

    //constructores
    public HistorialTrabajo() {
    }

    public HistorialTrabajo(Proyecto proyecto, Empleado empleado, Tarea tarea, int horasTrabajadas) {
        this.proyecto = proyecto;
        this.empleado = empleado;
        this.tarea = tarea;
        this.horasTrabajadas = horasTrabajadas;
    }
    //fin constructores

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public ArrayList<HistorialTrabajo> getHistorialTrabajoLista() {
        return historialTrabajoLista;
    }

    public void setHistorialTrabajoLista(ArrayList<HistorialTrabajo> historialTrabajoLista) {
        this.historialTrabajoLista = historialTrabajoLista;
    }


    //getters y setters

    //fin getters y setters
}
