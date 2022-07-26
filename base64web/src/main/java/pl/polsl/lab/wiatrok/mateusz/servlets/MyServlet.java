/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.lab.wiatrok.mateusz.servlets;

import pl.polsl.lab.wiatrok.mateusz.model.Model;
import java.io.*;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import pl.polsl.lab.wiatrok.mateusz.model.IllegalCharactersException;

/**
 * This is servlet responsible for checking data from user and passing forward
 *
 * @author Mateusz Wiatrok
 * @version 1.0
 */
@WebServlet("/Servlet")
public class MyServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
          response.setContentType("text/html; charset=ISO-8859-2");
        PrintWriter writer = response.getWriter();

        String input = request.getParameter("inputtext");
        
        Model model = new Model();
        
        try
        {
            model.isIllegal(input);
            getServletContext().getRequestDispatcher("/ForwardServlet").forward(request, response);
        }
        catch(IllegalCharactersException illegal){
            response.sendError(response.SC_EXPECTATION_FAILED, "String to decode/encode is incorrect");
        }      
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      
        processRequest(request, response);

    }
    
    /**
     * This is servlet method for handling GET requests from HTTP
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if exception occurs
     * @throws IOException if IO exception occurs
     */
     @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request,response);
        
    }
}


