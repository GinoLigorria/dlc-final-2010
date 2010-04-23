/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dlc;

import fileUtil.FileHelper;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import search.Diccionary;
import search.Search;

/**
 *
 * @author Juan Pablo
 */
public class SearchServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String query = "";
            if (request.getParameter("query") != null && !request.getParameter("query").equals("")) {
                query = request.getParameter("query");
            } else {
            }
            Search s = new Search();
            s.setQuery(query);
            Diccionary diccionary = FileHelper.readDiccionary("C:\\Users\\Juan Pablo\\Documents\\NetBeansProjects\\SearchEngine\\diccionary.idx");
            try{
            List<Search.Result> r = s.similarityAnalysis(diccionary.getWords(), diccionary.getInverseFrequency());

            int i = 0;
            Vector<String> strings = new Vector<String>();
            for (Search.Result result : r) {
                if (i < 6) {
                    strings.add(s.getFileContext(result.getDocument(), query));
                    result.setContext(s.getFileContext(result.getDocument(), query));
                } else {
                    break;
                }
            }
            request.getSession().setAttribute("result", r);


            }catch (Exception e){
                request.getSession().setAttribute("error", e.getMessage());
            }

            
            //request.getSession().setAttribute("StringContext", strings);

            response.sendRedirect("/Boocle/index.jsp");

        } catch (Exception ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        //Redireccionar a error
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}
