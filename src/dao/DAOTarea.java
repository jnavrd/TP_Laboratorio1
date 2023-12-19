package dao;

import entidades.Empleado;
import entidades.Tarea;
import service.ServiceEmpleado;
import service.ServiceException;

import java.sql.*;
import java.util.ArrayList;


public class DAOTarea implements IDAO<Tarea> {
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL="jdbc:h2:file:D:\\Facultad\\2023-segundo cuatri\\Lab 1\\baseTP";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";


    @Override
    public void guardar(Tarea elemento) throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO Tarea(titulo, descripcion, horasEstimadas, horasReales, estado, proyectoid) VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1, elemento.getTitulo());
            preparedStatement.setString(2, elemento.getDescripcion());
            preparedStatement.setInt(3, elemento.getHorasEstimadas());
            preparedStatement.setInt(4, elemento.getHorasReales());
            preparedStatement.setInt(5, elemento.getEstado());
            preparedStatement.setInt(6, elemento.getProyecto().getId());
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se agrego  " + resultado);
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            throw new DAOException("Error al insertar ");
        }
    }

    @Override
    public void modificar(Tarea elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET titulo = ?, descripcion = ?, horasEstimadas = ?, horasReales = ?, estado = ?, legempleado = ? WHERE id = ?");
            preparedStatement.setString(1, elemento.getTitulo());
            preparedStatement.setString(2, elemento.getDescripcion());
            preparedStatement.setInt(3, elemento.getHorasEstimadas());
            preparedStatement.setInt(4, elemento.getHorasReales());
            preparedStatement.setInt(5, elemento.getEstado());
            if(elemento.getEmpleado() == null)
                preparedStatement.setNull(6, Types.INTEGER);
            else
                preparedStatement.setInt(6, elemento.getEmpleado().getLegajo());
            preparedStatement.setInt(7, elemento.getId());
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modifico  " + resultado);
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            System.out.println(e.getMessage());
            throw new DAOException("Error al modificar ");
        }finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
                connection.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }
    }

    public void modificarEmpleado(Tarea elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET LegEmpleado = ? WHERE id = ?");
            if(elemento.getEmpleado() == null)
                preparedStatement.setNull(1, Types.INTEGER);
            else
                preparedStatement.setInt(1, elemento.getEmpleado().getLegajo());
            preparedStatement.setInt(2, elemento.getId());
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modifico  " + resultado);
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            System.out.println(e.getMessage());
            throw new DAOException("Error al modificar ");
        }finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
                connection.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }
    }

    public void eliminarEmpleado(Tarea elemento) throws ServiceException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET LegEmpleado = ? WHERE id = ?");
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setInt(2, elemento.getId());
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modifico  " + resultado);
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            System.out.println(e.getMessage());
            throw new ServiceException("Error al modificar ");
        }finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
                connection.close();
            }
            catch (SQLException s){
                throw new ServiceException("No se pudo conectar");
            }
        }
    }

    //para un agregar un generico ?
    public void asignarProyecto(Tarea elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET proyectoid = ? WHERE id = ?");
            preparedStatement.setInt(1, elemento.getProyecto().getId());
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se modifico  " + resultado);
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            System.out.println(e.getMessage());
            throw new DAOException("Error al modificar ");
        }finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
                connection.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }
    }
    @Override
    public void eliminar(int id) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM Tarea WHERE id = ?");
            preparedStatement.setInt(1, id);
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se elimino  " + resultado);
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            throw new DAOException("Error al eliminar ");
        }finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
                connection.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }
    }

    @Override
    public Tarea buscar(int id) throws DAOException {
        return null;
    }

    @Override
    public ArrayList<Tarea> buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Tarea tarea = null;
        ArrayList<Tarea> tareas = new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                System.out.println("rs " + rs.getString("titulo"));

                tarea = new Tarea();
                crearTarea(rs, tarea);

                ServiceEmpleado serviceEmpleado = new ServiceEmpleado(new DAOEmpleado());
                Empleado empleado = serviceEmpleado.buscar(rs.getInt("LegEmpleado"));
                tarea.setEmpleado(empleado);
                tareas.add(tarea);
            }

        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            throw new DAOException("Error al insertar ");
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return tareas;
    }

    public ArrayList<Tarea> buscarEnProyecto(int id) throws DAOException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea WHERE proyectoid = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Tarea> tareas = new ArrayList<>();
            while (rs.next()) {
                System.out.println("rs " + rs.getString("titulo"));
                Tarea tarea = new Tarea();
                crearTarea(rs, tarea);

                tareas.add(tarea);
            }
            return tareas;
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            throw new DAOException("Error al insertar ");
        } finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException s) {
                throw new DAOException("No se pudo conectar");
            }
        }
    }

    private void crearTarea(ResultSet rs, Tarea tarea) throws SQLException {
        tarea.setId(rs.getInt("id"));
        tarea.setTitulo(rs.getString("titulo"));
        tarea.setDescripcion(rs.getString("descripcion"));
        tarea.setHorasEstimadas(rs.getInt("horasEstimadas"));
        tarea.setHorasReales(rs.getInt("horasReales"));
        tarea.setEstado(rs.getInt("estado"));
    }
}