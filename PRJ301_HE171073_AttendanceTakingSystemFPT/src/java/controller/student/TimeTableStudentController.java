/**
 *
 * @author maiphuonghoang
 */
package controller.student;

import controller.authentication.BaseAuthenticationController;
import dal.AccountDBContext;
import dal.AttendDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Attend;
import model.Student;
import util.ChatGPT;


public class TimeTableStudentController extends BaseAuthenticationController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String accountId = BaseAuthenticationController.getAccountId(request,response);
        Student s = new AccountDBContext().getStudentFromAccount(accountId);
        String studentId = s.getStudentId();
        String date = request.getParameter("date");
        if (date == null) {
            date = ChatGPT.getToday();
        }
        Cookie accountCookie = new Cookie("accountId", studentId);
        accountCookie.setMaxAge(24 * 3600);
        response.addCookie(accountCookie);       
        Date selectdate = Date.valueOf(date);
        List<Date> sameWeekDays = ChatGPT.getDaysInSameWeekOfMonth(selectdate);
        ArrayList<Attend> attends = new AttendDBContext().getSessionStudentByWeek(studentId, sameWeekDays.get(0), sameWeekDays.get(sameWeekDays.size() - 1));
        request.setAttribute("sameWeekDays", sameWeekDays);
        request.setAttribute("attends", attends);
        request.setAttribute("selectdate", selectdate);
        request.setAttribute("studentId", studentId);
        request.getRequestDispatcher("../view/student/timetable.jsp").forward(request, response);
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }

}
