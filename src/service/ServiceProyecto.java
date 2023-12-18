package service;

import dao.DAOEmpleado;
import dao.DAOException;
import dao.DAOProyecto;
import entidades.Empleado;
import entidades.Proyecto;

import java.util.ArrayList;

public class ServiceProyecto {
    private DAOProyecto daoProyecto;

    public ServiceProyecto(DAOProyecto daoProyecto) {
        this.daoProyecto = daoProyecto;
    }

    public void guardar(Proyecto proyecto) throws ServiceException
    {
        try
        {
            daoProyecto.guardar(proyecto);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public int ultimoId() throws ServiceException
    {
        try
        {
            return daoProyecto.ultimoId();
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public void eliminar(int id) throws ServiceException
    {
        try
        {
            daoProyecto.eliminar(id);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public void modificar(Proyecto proyecto) throws ServiceException
    {
        try
        {
            daoProyecto.modificar(proyecto);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }
    public ArrayList<Proyecto> buscarTodos() throws ServiceException {
        try
        {
            return daoProyecto.buscarTodos();
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }
}
