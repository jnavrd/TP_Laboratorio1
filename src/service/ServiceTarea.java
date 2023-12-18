package service;

import dao.DAOException;
import dao.DAOTarea;
import entidades.Empleado;
import entidades.Tarea;

import java.util.ArrayList;
import java.util.List;

public class ServiceTarea {
    private DAOTarea daoTarea;

    public ServiceTarea(DAOTarea daoTarea) {
        this.daoTarea = daoTarea;
    }

    public void guardar(Tarea tarea) throws ServiceException {
        try {
            daoTarea.guardar(tarea);
        } catch (DAOException d) {
            throw new ServiceException(d.getMessage());
        }
    }

    public void modificar(Tarea tarea) throws ServiceException {
        try {
            daoTarea.modificar(tarea);
        } catch (DAOException d) {
            throw new ServiceException(d.getMessage());
        }
    }

    public void modificarEmpleado(Tarea tarea) throws ServiceException {
        try {
            daoTarea.modificarEmpleado(tarea);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public void eliminar(int id) throws ServiceException {
        try {
            daoTarea.eliminar(id);
        } catch (DAOException d) {
            throw new ServiceException(d.getMessage());
        }
    }

    public Tarea buscar(int id) throws ServiceException {
        try {
            return daoTarea.buscar(id);
        } catch (DAOException d) {
            throw new ServiceException(d.getMessage());
        }
    }

    public ArrayList<Tarea> buscarTodos() throws ServiceException {
        try {
            return daoTarea.buscarTodos();
        } catch (DAOException d) {
            throw new ServiceException(d.getMessage());
        }
    }

    public ArrayList<Tarea> buscarEnProyecto(int id) throws ServiceException
    {
        try {
            return daoTarea.buscarEnProyecto(id);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }
}