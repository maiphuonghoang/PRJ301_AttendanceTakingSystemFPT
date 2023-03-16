/**
 *
 *
 * @author maiphuonghoang
 */
package controller.instructor;

import controller.authentication.BaseAuthenticationController;
import dal.AccountDBContext;
import dal.SessionDBContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Instructor;
import model.Session;
import util.ChatGPT;

public class TimeTableInstructorController extends BaseAuthenticationController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Account account = (Account) request.getSession().getAttribute("account");
        String accountId = null;
        if (account == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cooky : cookies) {
                    if (cooky.getName().equals("username")) {
                        accountId = cooky.getValue();
                        break;
                    }
                }
            }
        } else {
            accountId = account.getUsername();
        }
        Instructor i = new AccountDBContext().getInstructorFromAccount(accountId);
        String lecturerId = i.getInstructorId();
        String date = request.getParameter("date");
        if (date == null) {
            date = ChatGPT.getToday();
        }
        Date selectdate = Date.valueOf(date);
        List<Date> sameWeekDays = ChatGPT.getDaysInSameWeekOfMonth(selectdate);
        ArrayList<Session> sessions = new SessionDBContext().getSessionInstructorByWeek(lecturerId, sameWeekDays.get(0), sameWeekDays.get(sameWeekDays.size() - 1));
        request.setAttribute("sameWeekDays", sameWeekDays);
        request.setAttribute("sessions", sessions);
        request.setAttribute("selectdate", selectdate);
        request.setAttribute("lecturerId", lecturerId);
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
