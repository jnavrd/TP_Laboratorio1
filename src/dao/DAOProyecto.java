package dao;

import entidades.Empleado;
import entidades.Proyecto;
import entidades.Tarea;
import service.ServiceEmpleado;
import service.ServiceException;

import java.sql.*;
import java.util.ArrayList;

public class DAOProyecto implements IDAO<Proyecto> {

    private String DB_JDBC_DRIVER="org.h2.Driver";
    private String DB_URL="jdbc:h2:file:D:\\Facultad\\2023-segundo cuatri\\Lab 1\\baseTP";
    private String DB_USER="sa";
    private String DB_PASSWORD="";

    @Override
    public void guardar(Proyecto elemento) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        //trabaja con el driver y a ese driver le digo q datos necesito
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement= connection.prepareStatement("INSERT INTO Proyecto(nombre, cantempleados, estado) VALUES(?,?,?)");
            preparedStatement.setString(1,elemento.getNombre());
            preparedStatement.setInt(2,elemento.getCantidadEmpleados());
            preparedStatement.setInt(3,elemento.getEstado());
            int resultado=preparedStatement.executeUpdate();
            System.out.println("Se agrego  "+resultado);
        }
        catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            //tiro la excepcion para arriba para que le llegue al usuario
            throw new DAOException("Error al insertar ");
        }
    }

    public int ultimoId() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = 0;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT MAX(id) FROM Proyecto");

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new DAOException("Error al obtener ultimo id");
        } finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
                connection.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }

        return id;
    }

    @Override
    public void modificar(Proyecto elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Proyecto SET nombre = ?, cantempleados = ?, estado = ? WHERE id = ?");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setInt(2, elemento.getCantidadEmpleados());
            preparedStatement.setInt(3, elemento.getEstado());
            preparedStatement.setInt(4, elemento.getId());
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
    public void eliminar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("DELETE FROM Proyecto WHERE id = ?");
            preparedStatement.setInt(1, id);
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se elimino  " + resultado);
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            System.out.println(e.getMessage());
            throw new DAOException("Error al eliminar");
        } finally {
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
    public Proyecto buscar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Proyecto proyecto = null;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Proyecto WHERE id = ?");
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                proyecto = new Proyecto();
                proyecto.setId(rs.getInt("id"));
                proyecto.setNombre(rs.getString("nombre"));
                proyecto.setCantidadEmpleados(rs.getInt("cantempleados"));
                proyecto.setEstado(rs.getInt("estado"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            System.out.println(e.getMessage());
            throw new DAOException("Error al eliminar");
        } finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
                connection.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }

        return proyecto;
    }

    @Override
    public ArrayList<Proyecto> buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Proyecto proyecto = null;
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Proyecto");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                proyecto = new Proyecto();
                proyecto.setId(rs.getInt("id"));
                proyecto.setNombre(rs.getString("nombre"));
                proyecto.setCantidadEmpleados(rs.getInt("cantempleados"));
                proyecto.setEstado(rs.getInt("estado"));
                proyecto.setTareas(new DAOTarea().buscarEnProyecto(proyecto.getId()));
                proyecto.setEmpleados(new DAOEmpleado().buscarEnProyecto(proyecto.getId()));
                proyectos.add(proyecto);
            }
        } catch (ClassNotFoundException | SQLException e) {
            //tiro la excepcion para arriba para que le llegue al usuario
            System.out.println(e.getMessage());
            throw new DAOException("Error al eliminar");
        } finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
                connection.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }
        return proyectos;
    }
}
