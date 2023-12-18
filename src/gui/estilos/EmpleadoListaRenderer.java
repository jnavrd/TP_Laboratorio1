package gui.estilos;

import entidades.Empleado;

public class EmpleadoListaRenderer extends ListaRenderer<Empleado> {
    String proyecto;
    @Override
    protected String ponerTexto(Empleado empleado) {
        if(empleado.getProyecto() == null)
            proyecto = "No asignado";
        else
            proyecto = "Asignado a " + empleado.getProyecto().getNombre();
        return "<html>" + empleado.getNombre() + "<br> Costo por hora: " + empleado.getCostoHora() + "<br>" + proyecto + "</html>";
    }
}
