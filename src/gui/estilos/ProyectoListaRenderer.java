package gui.estilos;

import entidades.Proyecto;
import entidades.Tarea;

import javax.swing.*;
import java.awt.*;

public class ProyectoListaRenderer extends ListaRenderer<Proyecto> {
    String estado;

    @Override
    protected String ponerTexto(Proyecto proyecto) {
        estado = stringEstado(proyecto.getEstado());
        return "<html>" + proyecto.getNombre() + "<br> Cantidad de Empleados: " + proyecto.getCantidadEmpleados() + "<br> Estado: " + estado + "</html>";
    }
}
