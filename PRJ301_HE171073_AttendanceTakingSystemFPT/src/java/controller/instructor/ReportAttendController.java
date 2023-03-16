/**
 *
 * @author maiphuonghoang
 */
package controller.instructor;

import controller.authentication.BaseAuthenticationController;
import dal.AttendDBContext;
import dal.GroupDBContext;
import dal.SessionDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import model.Attend;
import model.Group;
import model.Session;
import model.Student;

public class ReportAttendController extends BaseAuthenticationController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String groupName = request.getParameter("groupName");
        String courseId = request.getParameter("courseId");
        ArrayList<Attend> attends = new AttendDBContext().getReportAttendsOfGroup(groupName, courseId);
        LinkedHashMap<Student, Float> percents = new AttendDBContext().getReportAbsentPercentage(groupName, courseId);
        ArrayList<Session> sessions = new SessionDBContext().getSessionsOfCourse(groupName, courseId);
        ArrayList<Group> groups = new GroupDBContext().getGroupsByIntructor("sonnt5");
        request.setAttribute("attends", attends);
        request.setAttribute("courseId", courseId);
        request.setAttribute("groupName", groupName);
        request.setAttribute("groups", groups);
        request.setAttribute("sessions", sessions);
        request.setAttribute("percents", percents);
        request.getRequestDispatcher("../view/instructor/reportattend.jsp").forward(request, response);
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
