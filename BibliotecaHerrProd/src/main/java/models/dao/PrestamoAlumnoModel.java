/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.dao;

/**
 *
 * @author A5155456HP
 */
import controllers.PrestamosLibros;
import java.sql.*;
import java.util.ArrayList;
import models.Alumno;
import models.Libro;
import models.Conexion;
import models.Libro;
import models.PrestamoAlumno;
import models.PrestamoLibro;
import utils.FechaConvert;

public class PrestamoAlumnoModel {

    Conexion conexion = null;

    private ResultSet rs = null;
    private PreparedStatement ps;
    private Connection accesoDB;
    //  private Libro libro = null;

    private PrestamoLibro prestamoLibro = null;
    private ArrayList<Alumno> alumnosList;
    private ArrayList<Libro> librosList;
    private ArrayList<PrestamoLibro> prestamosList = null;

    private Alumno alu;
    private Libro lib;

    private PrestamosLibros prestamoDevueltoAlControlador = null;

    private static final String INSERT_PRESTAMO = "INSERT INTO prestamo_alumno(carnet_alumno, codigo_libro,"
            + " fecha_prestamo, fecha_devolucion, codigo_prestamo, cantidadprestamo)VALUES(?,?,?,?,?,?)";

    private static final String UPDATE_PRESTAMO = "UPDATE prestamo_alumno SET carnet_alumno = ?,"
            + " codigo_libro = ?, fecha_prestamo=?, fecha_devolucion =?, cantidadprestamo =? WHERE codigo_prestamo = ?";

    private static final String DELETE_PRESTAMO = "DELETE FROM prestamo_alumno WHERE codigo_prestamo = ?";

    private static final String SELECT_PRESTAMO_BY_ID = "SELECT * FROM prestamo_alumno WHERE codigo_prestamo= ?";

    private static final String SELECT_ALL_PRESTAMOS = "SELECT pa.codigo_prestamo, a.carnet, l.codigolibro, pa.fecha_prestamo, pa.fecha_devolucion, pa.cantidadprestamo FROM alumno a INNER JOIN prestamo_alumno pa ON a.carnet = pa.carnet_alumno INNER JOIN libro l ON pa.codigo_libro = l.codigolibro";

    //  private static final String SELECT_ALL_PRESTAMOS = "SELECT  * FROM prestamo_alumno";
    private static final String SELECT_LIBROS_COMBO = "SELECT * FROM libro";

    private static final String SELECT_ALUMNOS_COMBO = "SELECT * FROM alumno";

    // Constructor vacío
    public PrestamoAlumnoModel() throws SQLException, ClassNotFoundException {
        this.conexion = new Conexion();
    }

    public String insertPrestamoLibro(PrestamoLibro prestamoLibro) throws SQLException, ClassNotFoundException {

        String resultado;
        int resultado_insertar;
        try {

            this.conexion = new Conexion();
            this.conexion.getConexion();
            this.accesoDB = conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(INSERT_PRESTAMO);
            this.ps.setString(1, prestamoLibro.getAlumno().getCarnet());
            this.ps.setString(2, prestamoLibro.getLibro().getCodigolibro());
            this.ps.setDate(3, new Date(prestamoLibro.getFechaPrestamo().getTime()));
            this.ps.setDate(4, new Date(prestamoLibro.getFechaDevolucion().getTime()));
            // this.ps.setString(3, prestamoLibro.getFechaPrestamo().toString());
            //this.ps.setString(4, prestamoLibro.getFechaDevolucion().toString());
            this.ps.setString(5, prestamoLibro.getCodigoPrestamo());
            this.ps.setInt(6, prestamoLibro.getCantidadPrestamo());

            System.out.println("prestamolibro_insertar" + prestamoLibro);
            resultado_insertar = this.ps.executeUpdate();
            this.conexion.cerrarConexiones();
            if (resultado_insertar > 0) {
                resultado = "exito";
            } else {
                resultado = "error_insertar_prestamolibro";
            }
        } catch (SQLException e) {
            resultado = "error_exepcion";
            System.out.println("falló insertar" + e.getErrorCode());
            e.printStackTrace();
        }
        return resultado;
    }

    //public ArrayList<PrestamoLibro> selectAllPrestamoLibros(Integer estado, String quien) throws SQLException, ClassNotFoundException {
    public ArrayList<PrestamoLibro> selectAllPrestamoLibros() throws SQLException, ClassNotFoundException {

        this.prestamosList = new ArrayList();

        // ResultSet resultSet=null;
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_ALL_PRESTAMOS);
            // resultSet = this.ps.executeQuery();
            this.rs = ps.executeQuery();

            PrestamoLibro obj = null;
            Libro libro = null;
            Alumno alumno = null;

            while (this.rs.next()) {
                obj = new PrestamoLibro();
                alumno = new Alumno();
                libro = new Libro();
//SELECT pa.codigo_prestamo, a.carnet, l.codigolibro, pa.fecha_prestamo, pa.fecha_devolucion, pa.cantidadprestamo FROM alumno a INNER JOIN prestamo_alumno pa ON a.carnet = pa.carnet_alumno INNER JOIN libro l ON pa.codigo_libro = l.codigolibro
                obj.setCodigoPrestamo(rs.getString("codigo_prestamo"));
                obj.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                obj.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                obj.setCantidadPrestamo(rs.getInt("cantidadprestamo"));
                alumno.setCarnet(rs.getString("carnet"));
                obj.setAlumno((alumno));
                libro.setCodigolibro(rs.getString("codigolibro"));
                // libro.setTitulolibro(rs.getString("codigo_libro"));
                obj.setLibro(libro);
                this.prestamosList.add(obj);
            }

            this.conexion.cerrarConexiones();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.prestamosList;
    }

    public String updatePrestamoLibro(PrestamoLibro prestamoLibro, String codigo) throws SQLException {
        //String sql = "EXEC LibroModificar '"+codigo+"',?,?,?,?";

        System.out.println(prestamoLibro.getCodigoPrestamo());
        String resultado;
        int res_actualizar;

        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(UPDATE_PRESTAMO);

            /*
            "UPDATE prestamo_alumno SET carnet_alumno = ?,"
            + " codigo_libro = ?, fecha_prestamo=?, fecha_devolucion =?, cantidadprestamo =? WHERE codigo_prestamo = ?";

             */
            this.ps.setString(1, prestamoLibro.getAlumno().getCarnet());
            this.ps.setString(2, prestamoLibro.getLibro().getCodigolibro());
            this.ps.setDate(3,  prestamoLibro.getFechaPrestamo());
            this.ps.setDate(4, prestamoLibro.getFechaDevolucion());
            this.ps.setInt(5, prestamoLibro.getCantidadPrestamo());
            this.ps.setString(6, prestamoLibro.getCodigoPrestamo());

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

    //public String findById(String quien) throws SQLException, ClassNotFoundException {
    // public String findById(PrestamoLibro quien) throws SQLException, ClassNotFoundException {
    public PrestamoLibro findById(PrestamoLibro quien) throws SQLException, ClassNotFoundException {

        //   String resultado = "";
        // public PrestamoLibro findById(String quien) throws SQLException, ClassNotFoundException {
        try {
            this.accesoDB = this.conexion.getConexion();
            System.out.println("sql" + quien + UPDATE_PRESTAMO);
            this.ps = this.accesoDB.prepareStatement(SELECT_PRESTAMO_BY_ID);
            this.ps.setString(1, quien.getCodigoPrestamo());
            this.rs = ps.executeQuery();

            // resultado_insertar = this.ps.executeUpdate();
            // resultado = this.ps.executeQuery();
            // this.conexion.cerrarConexiones();
            //   if (resultado_insertar > 0) {
            //  resultado = "exito";
            // } else {
            // resultado = "error_insertar_prestamolibro";
            //}
            while (this.rs.next()) {
                this.prestamoLibro = new PrestamoLibro();
                this.alu = new Alumno();
                this.lib = new Libro();

                this.prestamoLibro.setCodigoPrestamo(this.rs.getString("codigo_prestamo"));
                this.prestamoLibro.setFechaPrestamo(this.rs.getDate("fecha_prestamo"));
                this.prestamoLibro.setFechaDevolucion(this.rs.getDate("fecha_devolucion"));
                this.prestamoLibro.setCantidadPrestamo(this.rs.getInt("cantidadprestamo"));
                this.alu.setCarnet(this.rs.getString("carnet_alumno"));
                this.prestamoLibro.setAlumno(this.alu);
                this.lib.setCodigolibro(rs.getString("codigo_libro"));
                this.prestamoLibro.setLibro(this.lib);
                // pasando el objeto this.prestamoLibro al constructor del controlador

                //PrestamosLibros p = new PrestamosLibros(this.prestamoLibro);
                //   resultado = "exito";
                //  this.prestamoDevueltoAlControlador = new PrestamosLibros(this.prestamoLibro);
            }
            this.conexion.cerrarConexiones();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Debe retornar el objeto encontrado
        // return this.prestamoLibro;
        return this.prestamoLibro;
    }

    public String deletePrestamoLibro(String id) throws SQLException {
        String resultado;
        int resultado_eliminar;
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(DELETE_PRESTAMO);
            this.ps.setString(1, id);
            //this.ps.executeUpdate();

            //System.out.println("autor_insertar" + autor);
            resultado_eliminar = this.ps.executeUpdate();
            this.conexion.cerrarConexiones();
            if (resultado_eliminar > 0) {
                resultado = "exito";
            } else {
                resultado = "error_eliminar_prestamo";
            }

        } catch (SQLException e) {
            resultado = "error";
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return resultado;
    }

    public ArrayList<Libro> getComboLibros() throws SQLException, ClassNotFoundException {
        this.librosList = new ArrayList();

        // ResultSet resultSet=null;
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_LIBROS_COMBO);
            // resultSet = this.ps.executeQuery();
            this.rs = ps.executeQuery();

            Libro obj = null;
            while (this.rs.next()) {
                obj = new Libro();
                obj.setCodigolibro(rs.getString("codigolibro"));
                obj.setExistencia(rs.getInt("existencia"));
                obj.setTitulolibro(rs.getString("titulolibro"));
                this.librosList.add(obj);
            }

            this.conexion.cerrarConexiones();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.librosList;
    }

    public ArrayList<Alumno> getComboAlumnos() throws SQLException, ClassNotFoundException {
        this.alumnosList = new ArrayList();

        // ResultSet resultSet=null;
        try {
            this.accesoDB = this.conexion.getConexion();
            this.ps = this.accesoDB.prepareStatement(SELECT_ALUMNOS_COMBO);
            // resultSet = this.ps.executeQuery();
            this.rs = ps.executeQuery();

            Alumno obj = null;
            while (this.rs.next()) {
                obj = new Alumno();

                obj.setCarnet(rs.getString("carnet"));
                obj.setNombre(rs.getString("nombre"));
                obj.setApellido(rs.getString("apellido"));

                this.alumnosList.add(obj);
            }

            this.conexion.cerrarConexiones();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.alumnosList;
    }

}
