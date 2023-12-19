package gui;

import entidades.Empleado;
import entidades.Proyecto;
import entidades.Tarea;
import gui.detalle.DetalleEmpleado;
import gui.detalle.DetalleTarea;
import gui.formulario.FormularioEmpleado;
import gui.formulario.FormularioProyecto;
import gui.formulario.FormularioTarea;
import gui.lista.ListaEmpleados;
import gui.lista.ListaEmpleadosAsignar;
import gui.lista.ListaProyectos;
import gui.lista.ListaTareas;
import service.ServiceException;

import javax.swing.*;
import java.awt.*;

public class PanelManager {
    JFrame jFrame;
    private Inicio inicio;
    private ListaEmpleados listaEmpleados;
    private ListaProyectos listaProyectos;
    private ListaTareas listaTareas;
    private FormularioEmpleado formularioEmpleado;
    private FormularioProyecto formularioProyecto;
    private FormularioTarea formularioTarea;
    private DetalleTarea detalleTarea;
    private DetalleEmpleado detalleEmpleado;
    private ReporteProyectos reporteProyectos;

    public PanelManager(int tipo) throws ServiceException {
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //para que el programa se cierre completamente con el boton de cerrar
        if(tipo == 1)
        {
            inicio = new Inicio(this);
            jFrame.setTitle("Inicio");
            mostrar(inicio);
        }
        if(tipo == 2)
        {
            listaProyectos = new ListaProyectos(this);
            jFrame.setTitle("Proyectos");
            mostrar(listaProyectos);
        }
        if(tipo == 3)
        {
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            formularioProyecto = new FormularioProyecto(this);
            jFrame.setTitle("Nuevo Proyecto");
            mostrar(formularioProyecto);
        }
        if(tipo == 4)
        {
            listaEmpleados = new ListaEmpleados(this);
            jFrame.setTitle("Empleados");
            mostrar(listaEmpleados);
        }
        if(tipo == 5)
        {
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            formularioEmpleado = new FormularioEmpleado(this);
            jFrame.setTitle("Nuevo Empleado");
            mostrar(formularioEmpleado);
        }
        if(tipo == 6)
        {
            reporteProyectos = new ReporteProyectos(this);
            jFrame.setTitle("Reporte de Proyectos");
            mostrar(reporteProyectos);
        }
    }

    public PanelManager(int tipo, Object objeto) throws ServiceException {
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        if(tipo == 1)
        {
            formularioTarea = new FormularioTarea(this, (Proyecto) objeto);
            jFrame.setTitle("Nueva Tarea");
            mostrar(formularioTarea);
        }
        if(tipo == 2)
        {
            listaTareas = new ListaTareas(this, (Proyecto) objeto);
            jFrame.setTitle("Tareas de " + ((Proyecto) objeto).getNombre());
            mostrar(listaTareas);
        }
        if(tipo == 3)
        {
            detalleTarea = new DetalleTarea((Tarea) objeto);
            jFrame.setTitle(((Tarea) objeto).getTitulo());
            mostrar(detalleTarea);
        }
        if(tipo == 4)
        {
            listaEmpleados = new ListaEmpleadosAsignar(this, (Proyecto) objeto);
            jFrame.setTitle("Empleados");
            mostrar(listaEmpleados);
        }
        if(tipo == 5)
        {
            detalleEmpleado = new DetalleEmpleado((Empleado) objeto);
            jFrame.setTitle(((Empleado) objeto).getNombre());
            mostrar(detalleEmpleado);
        }
    }

    public void mostrar(JPanel panel)
    {
        jFrame.getContentPane().removeAll();
        jFrame.getContentPane().add(BorderLayout.CENTER, panel);
        jFrame.getContentPane().validate();
        jFrame.getContentPane().repaint();
        if(panel instanceof Inicio)
            jFrame.setSize(350, 200);
        else
            jFrame.setSize(600, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
