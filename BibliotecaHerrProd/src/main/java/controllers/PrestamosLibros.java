/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author A5155456HP
 */
import models.Alumno;
import models.Libro;
import models.dao.PrestamoAlumnoModel;
import models.PrestamoAlumno;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.PrestamoLibro;

//import java.sql.Date;
//import utils.FechaConvert;
@WebServlet(name = "PrestamosLibros", value = "/PrestamosLibros")
public class PrestamosLibros extends HttpServlet {

    private ArrayList<Alumno> alumnosList;
    private ArrayList<Libro> librosList;
    private ArrayList<PrestamoLibro> prestamos;
    private Alumno alumno;
    private Libro libro;

    private PrestamoLibro prestamo;
    private PrestamoLibro prestamoRecuperado;

    //  private String op = "";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null) {
            response.sendRedirect("Login");
            return;
        } else {
            request.getRequestDispatcher("modulos/prestamoAlumno/index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //this.op = request.getParameter("opcion");
        String op = request.getParameter("opcion");
        this.alumnosList = new ArrayList<>();
        this.librosList = new ArrayList<>();

        //switch (this.op) {
        switch (op) {
            case "cargarCombos":
                try {
                JSONArray array = new JSONArray();
                JSONObject json = new JSONObject();

                String comboAlumnos = "";
                String comboLibros = "";
                PrestamoAlumnoModel prestamoModel = new PrestamoAlumnoModel();
                try {

                    // ResultSet alumnos = prestamoModel.getComboAlumnos();
                    // ResultSet libros = prestamoModel.getComboLibros();
                    this.alumnosList = prestamoModel.getComboAlumnos();
                    this.librosList = prestamoModel.getComboLibros();

                    for (Alumno alumnos : this.alumnosList) {
                        comboAlumnos += "<option value=" + alumnos.getCarnet() + ">" + alumnos.getNombre() + alumnos.getApellido() + "</option>";
                    }

                    for (Libro libros : this.librosList) {
                        comboLibros += "<option data-max=" + libros.getExistencia() + " value=" + libros.getCodigolibro() + ">" + libros.getTitulolibro() + "</option>";
                    }

                    json.put("resultado", "exito");
                    json.put("alumnos", comboAlumnos);
                    json.put("libros", comboLibros);

                } catch (SQLException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                }

                array.put(json);
                response.getWriter().write(array.toString());

            } catch (SQLException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;

            case "insertar": // Cuando también modifica.
                try {
                JSONArray array = new JSONArray();
                JSONObject json = new JSONObject();

                String codigoPrestamo = request.getParameter("codigoPrestamo");
                String carnetAlumno = request.getParameter("carnetAlumno");
                String codigoLibro = request.getParameter("codigoLibro");
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                String fecha1 = request.getParameter("fechaPrestamo");
                String fecha2 = request.getParameter("fechaDevolucion");
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                //   SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//Date fecha = formato.parse("23/11/2015");
//    Date fechaPrestamo = null;
//Date fechaDevolucion = null;
// try {
                java.util.Date fechaPrest = formato.parse(fecha1);
                java.util.Date fechaDev = formato.parse(fecha2);
// } catch (ParseException e) {
// throw new RuntimeException(e);
//}

                try {

                    //PrestamoAlumno objPrestamo = new PrestamoAlumno(new Alumno(carnetAlumno), new Libro(codigoLibro), fechaPrestamo, fechaDevolucion, codigoPrestamo, cantidad);
                    //  public PrestamoLibro(String codigoPrestamo, Date fechaPrestamo,
                    //    Date fechaDevolucion, int cantidadPrestamo, Alumno alumno, Libro libro) {
                    this.alumno = new Alumno(carnetAlumno);
                    this.libro = new Libro(codigoLibro);

                    java.sql.Date sqlFechaPrestamo = new java.sql.Date(fechaPrest.getTime());

                    java.sql.Date sqlFechaDevolucion = new java.sql.Date(fechaDev.getTime());

                    PrestamoLibro objPrestamo = new PrestamoLibro(codigoPrestamo, sqlFechaPrestamo, sqlFechaDevolucion, cantidad, this.alumno, this.libro);

                    PrestamoAlumnoModel prestamosModel = new PrestamoAlumnoModel();

                    String insertar = prestamosModel.insertPrestamoLibro(objPrestamo);
                    if (insertar.equals("exito")) {
                        json.put("resultado", "exito");

                        //  this.op = "";// LIMPIAR LA VARIABLE
                    } else {
                        json.put("resultado", "error");
                    }

                } catch (SQLException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                }

                array.put(json);
                response.getWriter().write(array.toString());

            } catch (ParseException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            
            
            case "si_actualizalo": // Cuando modifica.
                try {
                JSONArray array = new JSONArray();
                JSONObject json = new JSONObject();

                String codigoPrestamo = request.getParameter("codigoPrestamo");
                String carnetAlumno = request.getParameter("carnetAlumno");
                String codigoLibro = request.getParameter("codigoLibro");
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                String fecha1 = request.getParameter("fechaPrestamo");
                String fecha2 = request.getParameter("fechaDevolucion");
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                //   SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
//    Date fechaPrestamo = null;
//Date fechaDevolucion = null;
// try {
//SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                // Date parsed = format.parse("20110210");
                java.util.Date fechaPrest = formato.parse(fecha1);
                java.util.Date fechaDev = formato.parse(fecha2);

                java.sql.Date sqlFechaPrestamo = new java.sql.Date(fechaPrest.getTime());

                java.sql.Date sqlFechaDevolucion = new java.sql.Date(fechaDev.getTime());

                // //    Date fechaPrestamo = fecha1;
                //  Date fechaDevolucion = fecha2;
// } catch (ParseException e) {
// throw new RuntimeException(e);
//}
                try {

                    //PrestamoAlumno objPrestamo = new PrestamoAlumno(new Alumno(carnetAlumno), new Libro(codigoLibro), fechaPrestamo, fechaDevolucion, codigoPrestamo, cantidad);
                    //  public PrestamoLibro(String codigoPrestamo, Date fechaPrestamo,
                    //    Date fechaDevolucion, int cantidadPrestamo, Alumno alumno, Libro libro) {
                    this.alumno = new Alumno(carnetAlumno);
                    this.libro = new Libro(codigoLibro);

                    PrestamoLibro objPrestamo = new PrestamoLibro(codigoPrestamo, sqlFechaPrestamo, sqlFechaDevolucion, cantidad, this.alumno, this.libro);

                    PrestamoAlumnoModel prestamosModel = new PrestamoAlumnoModel();

                    String actualizar = prestamosModel.updatePrestamoLibro(objPrestamo, codigoPrestamo);
                    if (actualizar.equals("exito")) {
                        json.put("resultado", "exito");
                        // this.op = "";// LIMPIAR LA VARIABLE
                        
                    } else {
                        json.put("resultado", "error");
                    }

                } catch (SQLException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                }

                array.put(json);
                response.getWriter().write(array.toString());

            } catch (ParseException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "consultar":
                try {
                JSONArray array = new JSONArray();
                JSONObject json = new JSONObject();

                PrestamoAlumnoModel prestamoModel = new PrestamoAlumnoModel();

                String html = "<table class=\"table\" id=\"tabla_prestamos\""
                        + "class=\"table table-bordered dt-responsive nowrap\" width=\"100%\">\n"
                        + "  <thead>\n"
                        + "    <tr>\n"
                        + "      <th scope=\"col\">Codigo Prestamo</th>\n"
                        + "      <th scope=\"col\">Libro</th>\n"
                        + "      <th scope=\"col\">Alumno</th>\n"
                        + "      <th scope=\"col\">Fecha Prestamo</th>\n"
                        + "      <th scope=\"col\">Fecha Devolución</th>\n"
                        + "      <th scope=\"col\">Cantidad</th>\n"
                        + "      <th scope=\"col\">Acciones</th>\n"
                        + "    </tr>\n"
                        + "  </thead>\n"
                        + "  <tbody>";
                try {
                    try {
                        this.prestamos = prestamoModel.selectAllPrestamoLibros();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // ResultSet prestamos = prestamoModel.getAllPrestamos();
                    int cont = 0;

                    for (PrestamoLibro objPrestamo : this.prestamos) {
                        //  while ((prestamos.next())) {
                        cont++;
                        //   String fecha1 = FechaConvert.covertFecha(objPrestamo.getFechaPrestamo());
                        //   String fecha2 = PrestamoAlumno.covertFecha(prestamos.getString("fecha_devolucion"));
                        html += "  <tr>\n"
                                + "      <td>" + objPrestamo.getCodigoPrestamo() + "</td>\n"
                                + "      <td>" + objPrestamo.getLibro().getCodigolibro() + "</td>\n"
                                + "      <td>" + objPrestamo.getAlumno().getCarnet() + "</td>\n"
                                + "      <td>" + objPrestamo.getFechaPrestamo() + "</td>\n"
                                + "      <td>" + objPrestamo.getFechaDevolucion() + "</td>\n"
                                + "      <td>" + objPrestamo.getCantidadPrestamo() + "</td>\n"
                                + "<td>"
                                + "<div class='dropdown m-b-10'>"
                                + "<button class='btn btn-secondary dropdown-toggle'"
                                + " type='button' id='dropdownMenuButton' data-toggle='dropdown'  aria-haspopup='true'"
                                + "aria-expanded='false'> Seleccione</button>"
                                + "<div class='dropdown-menu' aria-labelledby='dropdownMenuButton'>"
                                + "<a class='dropdown-item btn_eliminar' data-id='" + objPrestamo.getCodigoPrestamo() + "' href='javascript:void(0) '>Eliminar</a>"
                                + "<a class='dropdown-item btn_editar' data-id='" + objPrestamo.getCodigoPrestamo() + "' href='javascript:void(0) '>Actualizar</a>"
                                + "</div>"
                                + "</div>"
                                + "</td>"
                                + " </tr>";
                    }

                    html += "  </tbody>\n"
                            + "</table>";

                    json.put("resultado", "exito");
                    json.put("tabla", html);
                    json.put("cantidad", cont);

                } catch (SQLException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                    //  } catch (ClassNotFoundException e) {
                    //  json.put("resultado", "error");
                    //   throw new RuntimeException(e);
                }

                array.put(json);
                response.getWriter().write(array.toString());

            } catch (SQLException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            case "editar_consultar":
                try {
                JSONArray array = new JSONArray();
                JSONObject json = new JSONObject();
                JSONObject prestamito = new JSONObject();

                PrestamoAlumnoModel prestamoModel = new PrestamoAlumnoModel();

                try {

                    String idPrestamo = request.getParameter("id");
                    this.prestamo = new PrestamoLibro(); // Es indispensable antes de setear sino da error
                    this.prestamo.setCodigoPrestamo(idPrestamo);
                    //   String resultado = prestamoModel.findById(this.prestamo);

                    this.prestamoRecuperado = new PrestamoLibro();
                    this.prestamoRecuperado = prestamoModel.findById(this.prestamo);

                    if (this.prestamoRecuperado != null) {

                        // this.prestamoRecuperado = new PrestamoLibro();
                        // while (prestamo.next()) {
                        prestamito.put("codigoPrestamo", this.prestamoRecuperado.getCodigoPrestamo());
                        prestamito.put("carnet", this.prestamoRecuperado.getAlumno().getCarnet());
                        prestamito.put("codigoLibro", this.prestamoRecuperado.getLibro().getCodigolibro());
                        prestamito.put("fechaPrestamo", this.prestamoRecuperado.getFechaPrestamo());
                        prestamito.put("fechaDevolucion", this.prestamoRecuperado.getFechaDevolucion());
                        prestamito.put("cantidad", this.prestamoRecuperado.getCantidadPrestamo());

                        json.put("resultado", "exito");
                        json.put("prestamo", prestamito);

                    }

                } catch (SQLException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                }

                array.put(json);
                response.getWriter().write(array.toString());
            } catch (SQLException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;

            case "eliminar":
                JSONArray array_aElimina = new JSONArray();
                JSONObject json_aElimina = new JSONObject();
                String resultado = "";
                PrestamoAlumnoModel prestamoModel = null;
                try {
                    prestamoModel = new PrestamoAlumnoModel();
                    String idElim = request.getParameter("id");
                    resultado = prestamoModel.deletePrestamoLibro(request.getParameter("id"));
                    if (resultado == "exito") {
                        json_aElimina.put("resultado", "exito");
                    } else {
                        json_aElimina.put("resultado", "error_eliminar");
                    }
                } catch (SQLException e) {
                    json_aElimina.put("resultado", "error_sql");
                    json_aElimina.put("exception", e.getMessage());
                } catch (ClassNotFoundException e) {
                    json_aElimina.put("resultado", "error_class");
                    json_aElimina.put("exception", e.getMessage());
                    throw new RuntimeException(e);
                }
                array_aElimina.put(json_aElimina);
                response.getWriter().write(array_aElimina.toString());
                break;
            /*
            case "editar":
                
                 try {

                JSONArray array = new JSONArray();
                JSONObject json = new JSONObject();

                PrestamoAlumnoModel prestamoModelo = new PrestamoAlumnoModel();

                String codigoPrestamo = request.getParameter("codigoPrestamo");
                String carnetAlumno = request.getParameter("carnetAlumno");
                String codigoLibro = request.getParameter("codigoLibro");
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                String fecha1 = request.getParameter("fechaPrestamo");
                String fecha2 = request.getParameter("fechaDevolucion");

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

                Date fechaPrestamo = null;
                Date fechaDevolucion = null;
                try {
                    fechaPrestamo = (Date) formato.parse(fecha1);
                    fechaDevolucion = (Date) formato.parse(fecha2);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                //   PrestamoAlumno objPrestamo = new PrestamoAlumno(new Alumno(carnetAlumno), new Libro(codigoLibro), fechaPrestamo, fechaDevolucion, codigoPrestamo, cantidad);
                PrestamoLibro objPrestamo = new PrestamoLibro(new Alumno(carnetAlumno), new Libro(codigoLibro), fechaPrestamo, fechaDevolucion, codigoPrestamo, cantidad);

                try {
                    String result = prestamoModelo.findById(objPrestamo);
                    //  String resultado = prestamoModel.findById(objPrestamo.getCodigoPrestamo());

                    if (result.equals("exito")) {
                        json.put("resultado", "exito");
                    } else {
                        json.put("resultado", "error");
                    }

                } catch (SQLException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    json.put("resultado", "error");
                    throw new RuntimeException(e);
                }

                array.put(json);
                response.getWriter().write(array.toString());

            } catch (SQLException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
            }
            //}
            break;

            default:
                break;
             */
        }

    }
    /*
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            JSONArray array = new JSONArray();
            JSONObject json = new JSONObject();

            PrestamoAlumnoModel prestamoModel = new PrestamoAlumnoModel();

            String codigoPrestamo = req.getParameter("codigoPrestamo");
            String carnetAlumno = req.getParameter("carnetAlumno");
            String codigoLibro = req.getParameter("codigoLibro");
            int cantidad = Integer.parseInt(req.getParameter("cantidad"));
            String fecha1 = req.getParameter("fechaPrestamo");
            String fecha2 = req.getParameter("fechaDevolucion");
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            Date fechaPrestamo = null;
            Date fechaDevolucion = null;
            try {
                fechaPrestamo = (Date) formato.parse(fecha1);
                fechaDevolucion = (Date) formato.parse(fecha2);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            //   PrestamoAlumno objPrestamo = new PrestamoAlumno(new Alumno(carnetAlumno), new Libro(codigoLibro), fechaPrestamo, fechaDevolucion, codigoPrestamo, cantidad);
            PrestamoLibro objPrestamo = new PrestamoLibro(new Alumno(carnetAlumno), new Libro(codigoLibro), fechaPrestamo, fechaDevolucion, codigoPrestamo, cantidad);

            try {
                String resultado = prestamoModel.findById(objPrestamo);
                //  String resultado = prestamoModel.findById(objPrestamo.getCodigoPrestamo());

                if (resultado.equals("exito")) {
                    json.put("resultado", "exito");
                } else {
                    json.put("resultado", "error");
                }

            } catch (SQLException e) {
                json.put("resultado", "error");
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                json.put("resultado", "error");
                throw new RuntimeException(e);
            }

            array.put(json);
            resp.getWriter().write(array.toString());

        } catch (SQLException ex) {
            Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PrestamosLibros.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("aplication/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        String filtro = request.getParameter("consultar_datos");
        String idPrestamoAeliminar = request.getParameter("id");
        System.out.println(filtro);
        if (filtro == null) {
            return;
        }
        switch (filtro) {

        }
    }
    /*

        try {
            PrestamoAlumnoModel prestamoModel = new PrestamoAlumnoModel();

            String idEliminar = request.getParameter("id");
            String eliminar = prestamoModel.deletePrestamoLibro(request.getParameter("id"));

            if (eliminar.equals("exito")) {
                json.put("resultado", "exito");
            } else {
                json.put("resultado", "error");
            }

        } catch (SQLException e) {
            json.put("resultado", "error");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            json.put("resultado", "error");
            throw new RuntimeException(e);
        }

        array.put(json);
        response.getWriter().write(array.toString());

    }

     */
}
