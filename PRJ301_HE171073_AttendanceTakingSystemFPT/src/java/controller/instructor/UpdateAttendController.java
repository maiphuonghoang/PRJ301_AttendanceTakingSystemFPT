/**
 *
 * @author maiphuonghoang
 */

package controller.instructor;

import controller.authentication.BaseAuthenticationController;
import dal.AttendDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Attend;


public class UpdateAttendController extends BaseAuthenticationController {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    } 


    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int sessionId = Integer.parseInt(request.getParameter("sessionId"));
        ArrayList<Attend> attends = new AttendDBContext().getTakedAttendsBySession(sessionId);
        request.setAttribute("attends", attends);
        request.setAttribute("number", attends.size());
        request.setAttribute("sessionId", sessionId);
        request.getRequestDispatcher("../view/instructor/updateattend.jsp").forward(request, response);

    } 


    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }


}
