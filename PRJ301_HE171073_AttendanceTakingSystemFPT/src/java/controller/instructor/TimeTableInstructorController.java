/**
 *jstl to format to get day of the week
 *
 * @author authentication
 */
package controller.instructor;

import controller.authentication.BaseAuthenticationController;
import dal.SessionDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Session;
import util.ChatGPT;

public class TimeTableInstructorController extends BaseAuthenticationController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String lecturerId = request.getParameter("lecturerId");
        String date = request.getParameter("date");
        if (date == null) {
            date = ChatGPT.getToday();
            lecturerId = "sonnt5";
        }
        Date selectdate = Date.valueOf(date);
        List<Date> sameWeekDays = ChatGPT.getDaysInSameWeekOfMonth(selectdate);
        ArrayList<Session> sessions = new SessionDBContext().getSessionByWeek(lecturerId, sameWeekDays.get(0), sameWeekDays.get(sameWeekDays.size() - 1));
        request.setAttribute("sameWeekDays", sameWeekDays);
        request.setAttribute("sessions", sessions);
        request.setAttribute("selectdate", selectdate);
        request.getRequestDispatcher("../view/instructor/timetable.jsp").forward(request, response);
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
