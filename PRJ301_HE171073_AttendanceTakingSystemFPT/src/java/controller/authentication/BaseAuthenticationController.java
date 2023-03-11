/**
 *
 * @author maiphuonghoang
 */
package controller.authentication;

import dal.AccountDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;

public abstract class BaseAuthenticationController extends HttpServlet {

    private boolean isAuth(HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account == null) {
            Cookie[] cookies = request.getCookies();//Cookie là 1 mảng các giắ trị 
            if (cookies != null) {
                String user = null;
                String pass = null;
                for (Cookie cooky : cookies) {
                    if (cooky.getName().equals("username")) {
                        user = cooky.getValue();
                    }
                    if (cooky.getName().equals("password")) {
                        pass = cooky.getValue();
                    }
                }
                return checkAccount(request, user);
            }
            return false;

        } else {

            return checkAccount(request, account.getUsername());
        }

    }

    public boolean checkAccount(HttpServletRequest request, String username) {
        String url = request.getServletPath();
        AccountDBContext db = new AccountDBContext();
        int num = db.getNumberOfRoles(username, url);
        return num >= 1;

    }

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isAuth(request)) {
            //bussiness
            processGet(request, response);
        } else {
            response.getWriter().println("access denied");
        }

    }

    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (isAuth(request)) {
            //bussiness
            processPost(request, response);
        } else {
            response.getWriter().println("access denied");
        }
    }

}
