/**
 *
 * @author maiphuonghoang
 */
package controller.authentication;

import dal.AccountDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/authentication/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Boolean remember = Boolean.parseBoolean(request.getParameter("remember"));
        Account account = new AccountDBContext().getAccount(username, password);
        if (account != null) {
            request.getSession().setAttribute("account", account);
            if (remember) {
//                ghi cookie vào máy người dùng 
                Cookie uCookie = new Cookie("username", username);
                uCookie.setMaxAge(24 * 3600);
                Cookie pCookie = new Cookie("password", password);
                pCookie.setMaxAge(24 * 3600);
                //ghi cookie vào trình duyệt máy người dùng 
                response.addCookie(uCookie);
                response.addCookie(pCookie);
            }
            
        } else {

            request.setAttribute("username", username);
            request.setAttribute("password", password);
            request.setAttribute("err", "Username or password incorrect");
            request.getRequestDispatcher("view/authentication/login.jsp").forward(request, response);
        }

    }
}
