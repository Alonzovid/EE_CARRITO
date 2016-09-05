/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controlador.BeanArticulo;
import Controlador.BeanProducto;
import Modelo.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dajua
 */
@WebServlet(name = "EliminarProducto", urlPatterns = {"/eliminar_producto"})
public class EliminarProducto extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            
            
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        
            HttpSession sesion = request.getSession(true);
            //Se revisara si el array existe en la variable de sesion, si ya existe
            //seguira aniadiendo sino crea uno
            ArrayList<BeanArticulo> articulos = sesion.getAttribute("carrito") == null ?  null : (ArrayList) sesion.getAttribute("carrito");

            if(articulos != null){
                for(BeanArticulo a: articulos){
                    if(a.getIdProducto() == idProducto){
                        articulos.remove(a);
                        break;
                    }
                }
            }

            double total = 0;
            Producto p = new Producto();
            for(BeanArticulo a: articulos){

                BeanProducto producto = p.producto_x_id(a.getIdProducto());
                total += a.getCantidad() * producto.getPrecio();

            }
            response.getWriter().print(Math.round(total * 100.0)/100.0);
        }
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
