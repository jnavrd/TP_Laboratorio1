package entidades;

public class Tarea {
    private  int id;
    private String titulo;
    private String descripcion;
    private int horasEstimadas;
    private int horasReales;
    private Proyecto proyecto;
    private Empleado empleado;
    private int estado; //-1 por no empezado, 0 en curso, 1 pausado, 2 terminado

    //metodos
    public void asignarEmpleado(Empleado empleado){
        this.empleado = empleado;
        empleado.setLibre(false); //al asignar un empleado, deja de estar libre
    }
    public void estado() {} //? marcar tarea en la interfaz grafica?

    //-----------------------------fin metodos-----------------------------------------

    //constructores
    public Tarea() {
        this.estado = -1;
    }

    public Tarea(String titulo, String descripcion, int horasEstimadas, Empleado empleado, Proyecto proyecto) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.horasEstimadas = horasEstimadas;
        this.empleado = empleado;
        this.proyecto = proyecto;
        this.estado = -1;
    }
    //----------------------------fin constructores---------------------------------------------

    //getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getHorasEstimadas() {
        return horasEstimadas;
    }

    public void setHorasEstimadas(int horasEstimadas) {
        this.horasEstimadas = horasEstimadas;
    }

    public int getHorasReales() {
        return horasReales;
    }

    public void setHorasReales(int horasReales) {
        this.horasReales = horasReales;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    //------------------------------fin getters y setters--------------------------------------------


    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", horasEstimadas=" + horasEstimadas +
                ", horasReales=" + horasReales +
                ", empleado=" + empleado +
                ", estado=" + estado +
                '}';
    }
}