package controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Inicio", value = "/Inicio")
public class Inicio extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session= request.getSession();
    if(session.getAttribute("username")==null){
        response.sendRedirect("Login");
        return;
    }else{
        //request.getRequestDispatcher("index.jsp").forward(request,response);
        
        // capturar datos del usuario y la contraseña y buscarlas en la base de datos..
        
    }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
