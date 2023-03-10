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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Account;


public abstract class BaseAuthenticationController extends HttpServlet {

    private boolean isAuth(HttpServletRequest request) {
//        Account account = (Account) request.getSession().getAttribute("account");
//        return account != null;
//        if (account == null) {
//            return false;
//        } else {
//            String url = request.getServletPath();
//            AccountDBContext db = new AccountDBContext();
//            int num = db.getNumberOfRoles(account.getUsername(), url);
//            return num>=1;
//            return true;
//        }
        
        return true;
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
            processGet(request, response);
        } else {
            response.getWriter().println("access denied");
        }
    }

}
