/**
 *
 * @author maiphuonghoang
 */
package controller.instructor;

import controller.authentication.BaseAuthenticationController;
import dal.AccountDBContext;
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
import model.Instructor;
import model.Session;
import model.Student;

public class ReportAttendController extends BaseAuthenticationController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        ArrayList<Attend> attends = new AttendDBContext().getReportAttendsOfGroup(groupId);
        LinkedHashMap<Student, Float> percents = new AttendDBContext().getReportAbsentPercentage(groupId);
        ArrayList<Session> sessions = new SessionDBContext().getSessionsOfCourse(groupId);
        String accountId = BaseAuthenticationController.getAccountId(request, response);
        Instructor i = new AccountDBContext().getInstructorFromAccount(accountId);
        String lecturerId = i.getInstructorId();
        ArrayList<Group> groups = new GroupDBContext().getGroupsByIntructor(lecturerId);
        request.setAttribute("attends", attends);
        request.setAttribute("groupId", groupId);
        request.setAttribute("groups", groups);
        request.setAttribute("sessions", sessions);
        request.setAttribute("percents", percents);
        request.setAttribute("lecturerId", lecturerId);
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
