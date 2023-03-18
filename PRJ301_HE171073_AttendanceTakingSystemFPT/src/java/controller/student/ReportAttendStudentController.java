/**
 *
 * @author maiphuonghoang
 */
package controller.student;

import controller.authentication.BaseAuthenticationController;
import dal.AccountDBContext;
import dal.AttendDBContext;
import dal.SessionDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Attend;
import model.Session;
import model.Student;

public class ReportAttendStudentController extends BaseAuthenticationController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String accountId = BaseAuthenticationController.getAccountId(request, response);
        Student s = new AccountDBContext().getStudentFromAccount(accountId);
        int groupId = Integer.parseInt(request.getParameter("groupId"));
        String studentId = s.getStudentId();
        ArrayList<Attend> attends = new AttendDBContext().getAttendsByStudent(groupId, studentId);
        ArrayList<Session> sessions = new SessionDBContext().getSessionsOfStudent(studentId);
        request.setAttribute("studentId", studentId);
        request.setAttribute("studentName", s.getStudentName());
        request.setAttribute("attends", attends);
        request.setAttribute("sessions", sessions);
        int numAbsent = 0;
        for (Attend attend : attends) {
            if (!attend.isStatus() && attend.getSessionId().isSessionStatus()) {
                numAbsent++;
            }
        }
        double percentage = (double)numAbsent / attends.size()*100;
        request.setAttribute("numAbsent", numAbsent);
        request.setAttribute("percentage", percentage);
        request.getRequestDispatcher("../view/student/reportattend.jsp").forward(request, response);
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
