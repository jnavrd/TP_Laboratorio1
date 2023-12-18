import dao.DAOException;
import dao.DAOProyecto;
import entidades.Empleado;
import entidades.Proyecto;
import gui.PanelManager;
import service.ServiceException;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ServiceException {
        /*DAOTarea tareaTable = new DAOTarea();
        Tarea tarea = new Tarea();
        tarea.setId(1);
        tarea.setTitulo("Tarea 1");
        tarea.setDescripcion("Descripcion 1");
        tarea.setHorasEstimadas(10);
        tarea.setHorasReales(5);
        tarea.setEstado(1);
        tarea.setEmpleado(new Empleado(1, "juan", 20));
        try{
            tareaTable.guardar(tarea);
        } catch (DAOException d)
        {
            System.out.println(d.getMessage());
        }*/
        /*DAOProyecto proyectoTable = new DAOProyecto();
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto 2");
        proyecto.setEstado(1);
        ArrayList<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado(1, "juan", 20));
        proyecto.setEmpleados(empleados);
        try{
            proyectoTable.guardar(proyecto);
        } catch (DAOException d)
        {
            System.out.println(d.getMessage());
        }*/

        PanelManager panel = new PanelManager(1);
    }
}