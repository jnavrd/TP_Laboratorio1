package dao;

import entidades.Empleado;

import java.sql.*;
import java.util.ArrayList;

public class DAOEmpleado implements IDAO<Empleado> {
    private String DB_JDBC_DRIVER="org.h2.Driver";
    private String DB_URL="jdbc:h2:file:D:\\Facultad\\2023-segundo cuatri\\Lab 1\\baseTP";
    private String DB_USER="sa";
    private String DB_PASSWORD="";

    @Override
    public void guardar(Empleado elemento) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement= connection.prepareStatement("INSERT INTO Empleado(nombre, costoHora, libre) VALUES(?,?,?)");
            preparedStatement.setString(1,elemento.getNombre());
            preparedStatement.setInt(2, elemento.getCostoHora());
            preparedStatement.setBoolean(3, elemento.isLibre());
            int resultado=preparedStatement.executeUpdate();
            System.out.println("Se agrego  " + resultado);
        }
        catch(ClassNotFoundException | SQLException e){
            //tiro la excepcion para arriba para que le llegue al usuario
            throw new DAOException("Error al insertar ");
        }
        finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }
    }

    @Override
    public void modificar(Empleado elemento) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement= connection.prepareStatement("UPDATE Empleado SET costoHora=?, libre=?, proyectoid = ? WHERE legajo=?");
            preparedStatement.setInt(1,elemento.getCostoHora());
            preparedStatement.setBoolean(2, elemento.isLibre());
            preparedStatement.setInt(3, elemento.getProyecto().getId());
            preparedStatement.setInt(4, elemento.getLegajo());
            int resultado=preparedStatement.executeUpdate();
            System.out.println("Se modifico  "+resultado);
        }
        catch(ClassNotFoundException | SQLException e){
            //tiro la excepcion para arriba para que le llegue al usuario
            throw new DAOException("Error al modificar ");
        }
        finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }
    }

    @Override
    public void eliminar(int legajo) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement= connection.prepareStatement("DELETE FROM Empleado WHERE legajo=?");
            preparedStatement.setInt(1,legajo);
            int resultado=preparedStatement.executeUpdate();
            System.out.println("Se elimino  "+resultado);
        }
        catch(ClassNotFoundException | SQLException e){
            //tiro la excepcion para arriba para que le llegue al usuario
            System.out.println(e.getMessage());
            throw new DAOException("Error al eliminar ");
        }
        finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }
    }

    @Override
    public Empleado buscar(int legajo) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Empleado empleado =null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Empleado WHERE legajo=?");
            preparedStatement.setInt(1,legajo);
            ResultSet rs=preparedStatement.executeQuery();
            if (rs.next()){
                empleado= new Empleado();
                empleado.setLegajo(rs.getInt("legajo"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setCostoHora(rs.getInt("costoHora"));
                empleado.setLibre(rs.getBoolean("libre"));
            }
        }
        catch (ClassNotFoundException | SQLException s){
            throw new DAOException("No se encuentra el empleado");
        }
        finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
            }
            catch (SQLException s){
                throw new DAOException("No se pudo conectar");
            }
        }
        return empleado;
    }

    //empleados disponibles
    public ArrayList<Empleado> buscarLibres() throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Empleado empleado =null;
        ArrayList<Empleado> empleados=new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Empleado WHERE libre = ?");
            preparedStatement.setBoolean(1, true);
            crearEmpleado(preparedStatement, empleados);
        }
        catch (ClassNotFoundException | SQLException s){
            System.out.println("No encuentran empleados");
        }
        finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
            }
            catch (SQLException s){
                System.out.println("No se pudo conectar");
            }
        }
        return empleados;
    }

    public ArrayList<Empleado> buscarSinAsignar() throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Empleado empleado =null;
        ArrayList<Empleado> empleados=new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Empleado WHERE proyectoid IS NULL");
            crearEmpleado(preparedStatement, empleados);
        }
        catch (ClassNotFoundException | SQLException s){
            System.out.println("No encuentran empleados");
        }
        finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
            }
            catch (SQLException s){
                System.out.println("No se pudo conectar");
            }
        }
        return empleados;
    }

    public ArrayList<Empleado> buscarEnProyecto(int proyectoId) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        Empleado empleado =null;
        ArrayList<Empleado> empleados=new ArrayList<>();
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Empleado WHERE proyectoid = ?");
            preparedStatement.setInt(1, proyectoId);
            crearEmpleado(preparedStatement, empleados);
        }
        catch (ClassNotFoundException | SQLException s){
            System.out.println("No encuentran empleados");
        }
        finally {
            //cierro conexion de base de datos
            try {
                preparedStatement.close();
            }
            catch (SQLException s){
                System.out.println("No se pudo conectar");
            }
        }
        return empleados;
    }

    @Override
    public ArrayList<Empleado> buscarTodos() throws DAOException {
    Connection connection=null;
    PreparedStatement preparedStatement=null;
    Empleado empleado =null;
    ArrayList<Empleado> empleados=new ArrayList<>();
    try{
        Class.forName(DB_JDBC_DRIVER);
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        preparedStatement=connection.prepareStatement("SELECT * FROM Empleado");
        crearEmpleado(preparedStatement, empleados);
    }
    catch(ClassNotFoundException | SQLException e){
        //tiro la excepcion para arriba para que le llegue al usuario
        System.out.println("Error al buscar todos");
    }
    finally {
        //cierro conexion de base de datos
        try {
            preparedStatement.close();
        }
        catch (SQLException s){
            System.out.println("No se pudo conectar");
        }
    }
    return empleados;
    }

    private void crearEmpleado(PreparedStatement preparedStatement, ArrayList<Empleado> empleados) throws SQLException, DAOException {
        Empleado empleado;
        ResultSet rs=preparedStatement.executeQuery();
        while (rs.next()){
            empleado= new Empleado();
            empleado.setLegajo(rs.getInt("legajo"));
            empleado.setNombre(rs.getString("nombre"));
            empleado.setCostoHora(rs.getInt("costoHora"));
            empleado.setLibre(rs.getBoolean("libre"));
            empleado.setProyecto(new DAOProyecto().buscar(rs.getInt("proyectoid")));
            empleados.add(empleado);
        }
    }
}
