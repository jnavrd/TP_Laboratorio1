package dao;

import java.util.ArrayList;

public interface IDAO <T> {

    public void guardar(T elemento) throws DAOException;
    public void modificar(T elemento) throws DAOException;
    public void eliminar(int id) throws DAOException;
    public T buscar(int id) throws DAOException;
    public ArrayList<T> buscarTodos() throws DAOException;

}
