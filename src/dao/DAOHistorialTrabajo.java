package dao;

import entidades.HistorialTrabajo;

import java.sql.*;
import java.util.ArrayList;

public class DAOHistorialTrabajo implements IDAO<HistorialTrabajo> {

    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL="jdbc:h2:file:D:\\Facultad\\2023-segundo cuatri\\Lab 1\\baseTP";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";
    @Override
    public void guardar(HistorialTrabajo elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO HistorialTrabajo(empleadoId, tareaId, proyectoid, horasTrabajadas) VALUES(?,?,?,?)");
            preparedStatement.setInt(1, elemento.getEmpleado().getLegajo());
            preparedStatement.setInt(2, elemento.getTarea().getId());
            preparedStatement.setInt(3, elemento.getProyecto().getId());
            preparedStatement.setInt(4, elemento.getHorasTrabajadas());
            int resultado = preparedStatement.executeUpdate();
            System.out.println("Se agrego  " + resultado);
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

    public int horasTrabajadasTotal(int idEmpleado) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int totalHorasTrabajadas = 0;

        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT SUM(horasTrabajadas) FROM HistorialTrabajo WHERE empleadoId = ?");
            preparedStatement.setInt(1, idEmpleado);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                totalHorasTrabajadas = rs.getInt(1);
            }
            System.out.println("(DAO)Total horas trabajadas: " + totalHorasTrabajadas);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException("Error al calcular total de horas trabajadas");
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

        return totalHorasTrabajadas;
    }

    public ArrayList<HistorialTrabajo> buscarEnProyecto(int idProyecto) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<HistorialTrabajo> historialTrabajos = new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM HistorialTrabajo WHERE proyectoid = ?");
            preparedStatement.setInt(1, idProyecto);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                HistorialTrabajo historialTrabajo = new HistorialTrabajo();
                historialTrabajo.setHorasTrabajadas(rs.getInt("horasTrabajadas"));
                historialTrabajo.setEmpleado(new DAOEmpleado().buscar(rs.getInt("empleadoId")));
                historialTrabajos.add(historialTrabajo);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
            throw new DAOException("Error al buscar historial de trabajo en proyecto");
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
        return historialTrabajos;
    }

    @Override
    public void modificar(HistorialTrabajo elemento) throws DAOException {

    }

    @Override
    public void eliminar(int id) throws DAOException {

    }

    @Override
    public HistorialTrabajo buscar(int id) throws DAOException {
        return null;
    }

    @Override
    public ArrayList<HistorialTrabajo> buscarTodos() throws DAOException {
        return null;
    }
}
