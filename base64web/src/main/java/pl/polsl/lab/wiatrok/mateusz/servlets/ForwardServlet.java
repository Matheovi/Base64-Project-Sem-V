/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.lab.wiatrok.mateusz.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.lab.wiatrok.mateusz.model.Model;

/**This is servlet that codes or decodes input string using model
 *
 * @author Mateusz Wiatrok
 * @version 1.0
 */
@WebServlet(name = "ForwardServlet", urlPatterns = {"/ForwardServlet"})
public class ForwardServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();    
        Model model = new Model();

        if (request.getParameter("encode") != null) {
            model.setOption(1);
        }
        if (request.getParameter("decode") != null) {
            model.setOption(2);
        }
        
        String input = request.getParameter("inputtext");

        String output = model.processStrings(input);
        
        int visits;
        writer.println("<html>");
        writer.println("<body>");
        writer.println("Hello!!!<br>Your input message:" + input + "<br><br>");
        writer.println("Your decoded (forwarded) message: " + output + "<br><br>");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            visits = 0;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("visits")) {
                    visits = Integer.parseInt(cookie.getValue());
                    break;
                    }
                }
            }
        else{
            visits = 0;
        }
        writer.println("How many times it was opened: " + visits);
        writer.println("</body>");
        writer.println("</html>");
        visits += 1;
            Cookie cookie = new Cookie("visits", Integer.toString(visits));
            response.addCookie(cookie);
            

        
        
        
        
        }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
