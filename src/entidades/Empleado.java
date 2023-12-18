package entidades;

public class Empleado {
    private int legajo;
    private String nombre;
    private int costoHora;
    private boolean libre;
    private Proyecto proyecto;
    private Tarea tarea;

    public Empleado() {
        this.libre = true;
    }

    public Empleado(String nombre, int costoHora) {
        this.nombre = nombre;
        this.costoHora = costoHora;
        this.libre = true;
    }

    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCostoHora() {
        return costoHora;
    }

    public void setCostoHora(int costoHora) {
        this.costoHora = costoHora;
    }

    public boolean isLibre() {
        return libre;
    }

    public void setLibre(boolean libre) {
        this.libre = libre;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    @Override
    public String toString() {
        return  nombre +
                ", costo Hora: " + costoHora;
    }
}
