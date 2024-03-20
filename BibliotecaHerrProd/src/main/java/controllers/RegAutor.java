/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

/**
 *
 * @author A5155456HP
 */
import models.Autor;
import models.dao.Obtener_AutoresDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/RegAutor")
public class RegAutor extends HttpServlet {

    private ArrayList<Autor> autoresList;

    
    private Autor au=null;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("username") == null) {
            resp.sendRedirect("Login");
            return;
        } else {
            req.getRequestDispatcher("modulos/mtto/autor/autor.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("aplication/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String filtro = req.getParameter("consultar_datos");

        System.out.println(filtro);
        if (filtro == null) {
            return;
        }
        switch (filtro) {
            case "si_registro":

                JSONArray array_autores = new JSONArray();
                JSONObject json_autores = new JSONObject();
                String resultado_insert = "";
                Autor autor = new Autor();

                try {
                    Obtener_AutoresDAO oa = new Obtener_AutoresDAO();
                    autor.setCodigoAutor(req.getParameter("codigoautor"));
                    autor.setNombreAutor(req.getParameter("nombreautor"));
                    autor.setApellidoAutor(req.getParameter("apellidoautor"));
                    System.out.println("autor filtro entró" + autor.getCodigoAutor());
                    Date fecha = null;
                    DateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        fecha = formatoF.parse(req.getParameter("fechanacimiento"));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    autor.setFechaNacimiento(fecha);
                    autor.setNacionalidad(req.getParameter("nacionalidad"));
                    resultado_insert = oa.insertAutor(autor);
                    if (resultado_insert.equals("exito")) {// se almacenaron los datos en la tabla de autor la bd
                        json_autores.put("resultado", "exito");
                        json_autores.put("codigoautor", autor.getCodigoAutor());
                        json_autores.put("nombreautor", autor.getNombreAutor());
                    } else {
                        json_autores.put("resultado", "error");
                        json_autores.put("resultado_insertar", resultado_insert);
                    }
                } catch (SQLException e) {
                    json_autores.put("resultado", "error_sql");
                    json_autores.put("error_mostrado", e.getMessage());
                    System.out.println("Error Code error:" + e.getErrorCode());
                    throw new RuntimeException();
                } catch (ClassNotFoundException e) {
                    json_autores.put("resultado", "error_class");
                    json_autores.put("error_mostrado", e.getMessage());
                    throw new RuntimeException();
                }
                array_autores.put(json_autores);
                resp.getWriter().write(array_autores.toString());
                break;
            case "si_consulta":
                JSONArray array_autor = new JSONArray();
                JSONObject json_autor = new JSONObject();
                String html = "";
                String el_estado = req.getParameter("estado");

                try {
                    Obtener_AutoresDAO obaut = new Obtener_AutoresDAO();
                    this.autoresList = new ArrayList();

                    this.autoresList = obaut.selectAllAutores(Integer.valueOf(el_estado), "todos");

                    //    ResultSet resultado = obaut.selectAllAutores(Integer.valueOf(el_estado), "todos");
                    // ResultSet resultado = obaut.selectAllAutores(Integer.valueOf(el_estado), "todos");
                    System.out.println("esta en resultado en RegAutor");
                    html += "<table id=\"tabla_autores\""
                            + "class=\"table table-bordered dt-responsive nowrap\""
                            + "cellspacing=\"0\" width=\"100%\">\n"
                            + "<thead>\n"
                            + "<tr>\n"
                            + "<th>Codigo</th>\n"
                            + "<th>Nombre</th>\n"
                            + "<th>Apellido</th>\n"
                            + "<th>Fecha de Nacimiento</th>\n"
                            + "<th>Nacionalidad</th>\n"
                            + "<th>Acciones</th>\n"
                            + "</tr>\n"
                            + "</thead>\n"
                            + "</tbody>";
                    int cont = 0;
                    for (Autor objAutor : this.autoresList) {
                        cont++;
                        html += "<tr>";
                        html += "<td>" + objAutor.getCodigoAutor() + "</td>";
                        html += "<td>" + objAutor.getNombreAutor() + "</td>";
                        html += "<td>" + objAutor.getApellidoAutor() + "</td>";
                        html += "<td>" + objAutor.getFechaNacimiento() + "</td>";
                        html += "<td>" + objAutor.getNacionalidad() + "</td>";

                        html += "<td>";

                        html += "<div class='dropdown m-b-10'>";
                        html += "<button class='btn btn-secondary dropdown-toggle'" + "type='button' id='dropdownMenuButton'"
                                + "data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'> Seleccione</button> ";
                        html += "<div class='dropdown-menu' aria-labelledby='dropdownMenuButton'>";

                        html += "<a class='dropdown-item btn_eliminar' data-id='" + objAutor.getCodigoAutor() +"'>Eliminar</a> ";
                        html += "<a class='dropdown-item btn_editar' data-id='" + objAutor.getCodigoAutor() +"'>Editar</a>";

                        html += "</div>";
                        html += "</div>";
                        html += "</td>";
                        html += "</tr>";
                    }// cierre for
                    html += "</tbody>\n"
                            + "\t\t </table>";
                    json_autor.put("resultado", "exito");
                    json_autor.put("tabla", html);
                    json_autor.put("cuantos", cont);
                    System.out.println("que tiene" + html);

                } catch (SQLException e) {
                    json_autor.put("resultado", "error sql");
                    json_autor.put("error", e.getMessage());
                    json_autor.put("code error", e.getErrorCode());
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    json_autor.put("resultado", "class not found");
                    json_autor.put("error", e.getMessage());
                    throw new RuntimeException(e);
                }
                array_autor.put(json_autor);
                resp.getWriter().write(array_autor.toString());
                break;
            case "si_actualizalo":
                JSONArray array_actulizar = new JSONArray();
                JSONObject json_actualizar = new JSONObject();
              this.au = new Autor();
                String result_actualizar = "";
                try {
                    Obtener_AutoresDAO naut = new Obtener_AutoresDAO();
                    this.au.setCodigoAutor(req.getParameter("llave_persona"));
                    this.au.setNombreAutor(req.getParameter("nombreautor"));
                    this.au.setApellidoAutor(req.getParameter("apellidoautor"));
                    Date fechaU = null;
                    DateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        fechaU = formatoF.parse(req.getParameter("fechanacimiento"));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    this.au.setFechaNacimiento(fechaU);
                    this.au.setNacionalidad(req.getParameter("nacionalidad"));
                    // result_actualizar = naut.updateAutor(au, req.getParameter("llave_persona"));

                    //String result_actualizar;
                    result_actualizar= naut.updateAutor(this.au, req.getParameter("llave_persona"));
                    
                    if ("exito".equals(result_actualizar)) {
                        json_actualizar.put("resultado", "exito");
                        json_actualizar.put("codigoautor", this.au.getCodigoAutor());
                        json_actualizar.put("nombreautor", this.au.getNombreAutor());
                        json_actualizar.put("apellidoautor", this.au.getApellidoAutor());
                        json_actualizar.put("fechanacimiento", this.au.getFechaNacimiento());
                        json_actualizar.put("nacionalidad", this.au.getNacionalidad());
                    } else {
                        json_actualizar.put("resultado", "error_sql");
                        json_actualizar.put("resultado_actualizar", result_actualizar);
                    }
                } catch (SQLException e) {
                    json_actualizar.put("resultado", "error_sql");
                    json_actualizar.put("error_mostrado", e.getMessage());
                    System.out.println("Error mostrado: " + e);
                    System.out.println("Error Code error: " + e.getErrorCode());
                    throw new RuntimeException();
                } catch (ClassNotFoundException e) {
                    json_actualizar.put("resultado", "error_class");
                    json_actualizar.put("error_mostrado", e);
                    throw new RuntimeException(e);
                }
                array_actulizar.put(json_actualizar);
                resp.getWriter().write(array_actulizar.toString());
                break;

            case "si_autor_especifico":
                JSONArray array_especifico = new JSONArray();
                JSONObject json_especifico = new JSONObject();
                Obtener_AutoresDAO outAutor = null;
                try {
                    outAutor = new Obtener_AutoresDAO();
                    // ResultSet res_indiv = outAutor.findById(req.getParameter("id"));

                    Autor res_indiv = outAutor.findById(req.getParameter("id"));

                    // while (res_indiv.next()) {
                    json_especifico.put("resultado", "exito");
                    json_especifico.put("id_persona", res_indiv.getCodigoAutor());
                    json_especifico.put("codigoautor", res_indiv.getCodigoAutor());
                    json_especifico.put("nombreautor", res_indiv.getNombreAutor());
                    json_especifico.put("apellidoautor", res_indiv.getApellidoAutor());
                    json_especifico.put("fechanacimiento", res_indiv.getFechaNacimiento());
                    json_especifico.put("nacionalidad", res_indiv.getNacionalidad());
                    //  }
                } catch (SQLException e) {
                    json_especifico.put("resultado", "error_sql");
                    json_especifico.put("error_mostrado", e.getMessage());
                    System.out.println("Error mostrado: " + e);
                    System.out.println("Error Code error: " + e.getErrorCode());
                    System.out.println("Error excepcion: " + e);
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    json_especifico.put("resultado", "error_class");
                    json_especifico.put("error_mostrado", e);
                    throw new RuntimeException(e);
                }
                array_especifico.put(json_especifico);
                resp.getWriter().write(array_especifico.toString());
                break;
            case "si_eliminalo":
                JSONArray array_aElimina = new JSONArray();
                JSONObject json_aElimina = new JSONObject();
                String resultado = "";
                Obtener_AutoresDAO obtA = null;
                try {
                    obtA = new Obtener_AutoresDAO();
                    resultado = obtA.deleteAutor(req.getParameter("id"));
                    if ("exito".equals(resultado)) {
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
                resp.getWriter().write(array_aElimina.toString());
                break;
        }
    }
}
