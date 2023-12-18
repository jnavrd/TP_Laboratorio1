package service;

import dao.DAOException;
import dao.DAOHistorialTrabajo;
import entidades.HistorialTrabajo;

public class ServiceHistorialTrabajo {
    private DAOHistorialTrabajo daoHistorialTrabajo;

    public ServiceHistorialTrabajo(DAOHistorialTrabajo daoHistorialTrabajo) {
        this.daoHistorialTrabajo = daoHistorialTrabajo;
    }

    public void guardar(HistorialTrabajo historialTrabajo) throws ServiceException
    {
        try
        {
            daoHistorialTrabajo.guardar(historialTrabajo);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public HistorialTrabajo buscar(int id) throws ServiceException
    {
        try
        {
            return daoHistorialTrabajo.buscar(id);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }

    public int horasTrabajadasTotal(int idEmpleado) throws ServiceException
    {
        try
        {
            return daoHistorialTrabajo.horasTrabajadasTotal(idEmpleado);
        } catch (DAOException d)
        {
            throw new ServiceException(d.getMessage());
        }
    }
}
