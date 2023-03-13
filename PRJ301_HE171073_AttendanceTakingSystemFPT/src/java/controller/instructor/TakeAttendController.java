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
import java.sql.Date;
import java.util.ArrayList;
import model.Attend;
import model.Session;
import model.Student;
import util.ChatGPT;

public class TakeAttendController extends BaseAuthenticationController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int sessionId = Integer.parseInt(request.getParameter("sessionId"));
        ArrayList<Attend> attends = new AttendDBContext().getNotTakeAttendsByeSession(sessionId);
        request.setAttribute("attends", attends);
        request.setAttribute("number", attends.size());
        request.setAttribute("sessionId", sessionId);
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
        int sessionId = Integer.parseInt(request.getParameter("sessionId"));
        ArrayList<Attend> takedStatus = new ArrayList<>();
        int sizeGroup = Integer.parseInt(request.getParameter("number"));
        for (int i = 0; i < sizeGroup; i++) {
            Student s = Student.builder().studentId(request.getParameter("studentId" + i)).build();
            Session ses = Session.builder().sessionId(sessionId).build();
            boolean status = (request.getParameter("status" + i)).equals("present");
            takedStatus.add(Attend.builder()
                    .studentId(s)
                    .sessionId(ses)
                    .status(status)
                    .comment(request.getParameter("comment" + i))
                    .recordTime(ChatGPT.getCurrentDateTime())
                    .build());
            System.out.println(s.getStudentId());
            System.out.println(i);
        }

        ArrayList<Attend> takedStatusStudents = new AttendDBContext().insertTakedAttendsBySession(sessionId, takedStatus);
        System.out.println("insert xong");
        System.out.println(takedStatusStudents);
        System.out.println("ok");

        for (Attend takedStatusStudent : takedStatus) {
            System.out.println(takedStatusStudent);
        }
        for (Attend takedStatusStudent : takedStatusStudents) {
            System.out.println("ok4");
            System.out.println(takedStatusStudent);
        }
    }

}
