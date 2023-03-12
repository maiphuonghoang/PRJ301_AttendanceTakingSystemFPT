/**
 *
 * @author maiphuonghoang 
 */
package controller.instructor;

import controller.authentication.BaseAuthenticationController;
import dal.AttendDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Attend;

public class TakeAttendController extends BaseAuthenticationController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int sessionId = Integer.parseInt(request.getParameter("sessionId"));        
        ArrayList<Attend> attends = new AttendDBContext().getAllAttends(sessionId);
        request.setAttribute("number", attends.size());
        request.setAttribute("attends", attends);
        request.getRequestDispatcher("../view/instructor/takeattend.jsp").forward(request, response);
    } 


    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 


    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }


}
