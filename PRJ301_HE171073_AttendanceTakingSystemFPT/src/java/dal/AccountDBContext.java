/**
 *
 * @author maiphuonghoang
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Account;


public class AccountDBContext extends DBContext {

    public Account getAccount(String username, String password) {
        try {
            String sql = "SELECT [username], [password] from [Account] where username = ? and password = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account(rs.getString(1), rs.getString(2));
                return account;
            }
        } catch (SQLException ex) {
            System.out.println("loi lay ra account");
        }
        return null;
    }

    public int getNumberOfRoles(String username, String url) {
        try {
            String sql = "SELECT COUNT(*) AS Total FROM Account a \n"
                    + "INNER JOIN Account_Role ar on a.username = ar.username \n"
                    + "INNER JOIN [Role] g on g.roleId = ar.roleId\n"
                    + "INNER JOIN [Role_Feature] rf on rf.roleId = g.roleId\n"
                    + "INNER JOIN [Feature] f on rf.featureId = f.featureId \n"
                    + "WHERE a.username = ? AND f.url = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, url);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            System.out.println("loi lay ra so luong role");
        }
        return -1;
    }
    public static void main(String[] args) {
        int num = new AccountDBContext().getNumberOfRoles("HE171073@fpt.edu.vn", "/student/timetable");
        System.out.println(num);
        int num2 = new AccountDBContext().getNumberOfRoles("sonnt5@fpt.edu.vn", "/student/timetable");
        System.out.println(num2);
    }

}
