/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.dao;

/**
 *
 * @author A5155456HP
 */
import java.sql.*;
import java.util.ArrayList;
import models.Autor;
import models.Conexion;

public class Obtener_AutoresDAO {

    Conexion conexion = null;
    private ArrayList<Autor> autoresList;
    private ResultSet rs = null;
    private PreparedStatement ps;
    private Connection accesoDB;
    private Autor autor = null;

    private static final String INSERT_AUTOR = "INSERT INTO autor(codigoautor, nombreautor,"
            + " apellidoautor, fechanacimiento, nacionalidad)VALUES(?,?,?,?,?)";

    private static final String UPDATE_AUTOR = "UPDATE autor SET nombreautor = ?,"
            + " apellidoautor = ?,fechanacimiento =?, nacionalidad =? WHERE codigoautor= ?";

    private static final String DELETE_AUTOR = "DELETE FROM autor WHERE codigoautor= ?";

    private static final String SELECT_AUTOR_BY_ID = "SELECT * FROM autor WHERE codigoautor= ?";

    private static final String SELECT_ALL_AUTORES = "SELECT * FROM autor";

    // Constructor vacío
    public Obtener_AutoresDAO() throws SQLException, ClassNotFoundException {
        this.conexion = new Conexion();
    }

    public String insertAutor(Autor autor) throws SQLException, ClassNotFoundException {

        String resultado;
        int resultado_insertar;
        try {
            this.conexion = new Conexion();
            this.conexion.getConexion();
            this.accesoDB = conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(INSERT_AUTOR);
            this.ps.setString(1, autor.getCodigoAutor());
            this.ps.setString(2, autor.getNombreAutor());
            this.ps.setString(3, autor.getApellidoAutor());
            this.ps.setDate(4, new Date(autor.getFechaNacimiento().getTime()));
            this.ps.setString(5, autor.getNacionalidad());
            System.out.println("autor_insertar" + autor);
            resultado_insertar = this.ps.executeUpdate();
            this.conexion.cerrarConexiones();
            if (resultado_insertar > 0) {
                resultado = "exito";
            } else {
                resultado = "error_insertar_autor";
            }
        } catch (SQLException e) {
            resultado = "error_exepcion";
            System.out.println("falló insertar" + e.getErrorCode());
            e.printStackTrace();
        }
        return resultado;
    }

    public ArrayList<Autor> selectAllAutores(Integer estado, String quien) throws SQLException, ClassNotFoundException {
        this.autoresList = new ArrayList();

        // ResultSet resultSet=null;
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_ALL_AUTORES);
            // resultSet = this.ps.executeQuery();
            this.rs = ps.executeQuery();

            Autor obj = null;
            while (this.rs.next()) {
                obj = new Autor();

                obj.setCodigoAutor(rs.getString("codigoautor"));
                obj.setNombreAutor(rs.getString("nombreautor"));
                obj.setApellidoAutor(rs.getString("apellidoautor"));
                obj.setFechaNacimiento(rs.getDate("fechanacimiento"));
                obj.setNacionalidad(rs.getString("nacionalidad"));

                this.autoresList.add(obj);
            }

            this.conexion.cerrarConexiones();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.autoresList;
    }

    public String updateAutor(Autor autor, String codigo) throws SQLException {
        //String sql = "EXEC AutorModificar '"+codigo+"',?,?,?,?";

        System.out.println(autor.getCodigoAutor());
        String resultado;
        int res_actualizar;

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(UPDATE_AUTOR);
            //"UPDATE autor SET nombreautor = ?, apellidoautor = ?,fechanacimiento =?, nacionalidad =? WHERE codigoautor= ?";
            this.ps.setString(1, autor.getNombreAutor());
            this.ps.setString(2, autor.getApellidoAutor());
            this.ps.setDate(3, new Date(autor.getFechaNacimiento().getTime()));
            this.ps.setString(4, autor.getNacionalidad());
            this.ps.setString(5, autor.getCodigoAutor());
            //this.ps.executeUpdate();
            res_actualizar = this.ps.executeUpdate();
            System.out.println(res_actualizar);
            if (res_actualizar > 0) {
                resultado = "exito";
            } else {
                resultado = "error_actualizar";
            }
            //con.commit();
        } catch (SQLException e) {
            // con.rollback();
            resultado = "error_exception";
            e.printStackTrace();
        }
        return resultado;

    }

     public Autor findById(String quien) throws SQLException, ClassNotFoundException {
        try {
            this.accesoDB = this.conexion.getConexion();
            System.out.println("sql" + quien + UPDATE_AUTOR);
            this.ps = this.accesoDB.prepareStatement(SELECT_AUTOR_BY_ID);
            this.ps.setString(1, quien);
            this.rs = ps.executeQuery();
            while (this.rs.next()) {
                this.autor = new Autor();
                this.autor.setCodigoAutor(rs.getString("codigoautor"));
                this.autor.setNombreAutor(rs.getString("nombreautor"));
                this.autor.setApellidoAutor(rs.getString("apellidoautor"));
                this.autor.setFechaNacimiento(rs.getDate("fechanacimiento"));
                this.autor.setNacionalidad(rs.getString("nacionalidad"));
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Debe retornar el objeto encontrado
        return this.autor;
    }

    public String deleteAutor(String id) throws SQLException {
        String resultado;
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(DELETE_AUTOR);
            this.ps.setString(1, id);
            this.ps.executeUpdate();
            resultado = "exito";
        } catch (SQLException e) {          
            resultado = "error";
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultado;
    }
}