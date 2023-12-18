package gui.estilos;

import entidades.Proyecto;
import service.ServiceException;

public class ReporteListaRenderer extends ListaRenderer<Proyecto> {
    String estado;
    int horasEstimadas, horasTotales;
    @Override
    protected String ponerTexto(Proyecto proyecto) {
        estado = stringEstado(proyecto.getEstado());
        horasEstimadas = proyecto.calcularHorasEstimadas();
        horasTotales = proyecto.calcularHorasTotales();

        return "<html>" + proyecto.getNombre() + "<br> Cantidad de Empleados: " + proyecto.getCantidadEmpleados() + "<br> Horas Estimadas: " + horasEstimadas + "<br> Horas reales: " + horasTotales + "<br> Estado: " + estado + "</html>";
    }
}
