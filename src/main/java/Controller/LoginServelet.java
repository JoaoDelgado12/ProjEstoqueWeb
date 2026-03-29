/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Dao.UserDAO;
import Model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author Master
 */

@WebServlet("/login")
public class LoginServelet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response) 
            throws ServletException, IOException {
        String usuario = request.getParameter("user");
        String passw = request.getParameter("passw");
       
        UserModel userModel = new UserModel();
        userModel.setUsername(usuario);
        userModel.setSenha(passw);
        
        UserDAO DAO = new UserDAO();
        
        if(DAO.validarLogin(userModel)){
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
            
            response.sendRedirect("pages/menu.html");
        } else{
            response.sendRedirect("index.html");
        }
        
    }
    
}
