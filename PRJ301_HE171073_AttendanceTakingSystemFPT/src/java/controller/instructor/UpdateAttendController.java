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
import model.Session;
import model.Student;
import util.ChatGPT;

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
        request.setAttribute("sessionId", sessionId);
        request.getRequestDispatcher("../view/instructor/updateattend.jsp").forward(request, response);

    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sessionId = Integer.parseInt(request.getParameter("sessionId"));
        ArrayList<Attend> attends = new AttendDBContext().getTakedAttendsBySession(sessionId);
        request.setAttribute("attends", attends);
        request.setAttribute("sessionId", sessionId);
        ArrayList<Attend> changeattends = new ArrayList<>();
        Session ses = Session.builder().sessionId(sessionId).build();
        for (int i = 0; i < attends.size(); i++) {
            Student s = Student.builder().studentId(request.getParameter("studentId" + i)).build();
            boolean changedStatus = (request.getParameter("status" + i)).equals("present");
            String changedComment = request.getParameter("comment" + i);
            if (attends.get(i).isStatus() != changedStatus || !attends.get(i).getComment().equals(changedComment)) {
                changeattends.add(Attend.builder()
                        .sessionId(ses)
                        .studentId(s)
                        .status(changedStatus)
                        .comment(changedComment)
                        .recordTime(ChatGPT.getCurrentDateTime())
                        .build());
            }
        }
        new AttendDBContext().updateTakedAttendsBySession(sessionId, changeattends);
        response.sendRedirect("viewattend?sessionId=" + sessionId);
    }

}
