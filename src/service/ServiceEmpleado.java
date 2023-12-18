package service;

import dao.DAOEmpleado;
import dao.DAOException;
import entidades.Empleado;

import java.util.ArrayList;

public class ServiceEmpleado {
    private DAOEmpleado daoEmpleado;

    public ServiceEmpleado(DAOEmpleado daoEmpleado) {
        this.daoEmpleado = daoEmpleado;
    }

    public void guardar(Empleado empleado) throws ServiceException
    {
        try
        {
            daoEmpleado.guardar(empleado);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public void modificar(Empleado empleado) throws ServiceException
    {
        try
        {
            daoEmpleado.modificar(empleado);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public void eliminar(int legajo ) throws ServiceException
    {
        try
        {
            daoEmpleado.eliminar(legajo);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public Empleado buscar(int legajo) throws ServiceException
    {
        try
        {
            return daoEmpleado.buscar(legajo);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public ArrayList<Empleado> buscarLibres() throws ServiceException
    {
        try
        {
            return daoEmpleado.buscarLibres();
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public ArrayList<Empleado> buscarSinAsignar() throws ServiceException
    {
        try
        {
            return daoEmpleado.buscarSinAsignar();
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public ArrayList<Empleado> buscarTodos() throws ServiceException
    {
        try
        {
            return daoEmpleado.buscarTodos();
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }
}
