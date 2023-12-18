package gui.estilos;

import entidades.Tarea;

import javax.swing.*;
import java.awt.*;

public class TareaListaRenderer extends ListaRenderer<Tarea> {
    String estado;
    @Override
    protected String ponerTexto(Tarea tarea) {
        estado = stringEstado(tarea.getEstado());
        return "<html>" + tarea.getTitulo() + "<br> Descripcion: " + tarea.getDescripcion() + "<br> Estado: " + estado + "</html>";
    }
}
