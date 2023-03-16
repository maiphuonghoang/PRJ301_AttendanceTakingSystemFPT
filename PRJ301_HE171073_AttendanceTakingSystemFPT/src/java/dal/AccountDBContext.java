/**
 *
 * @author maiphuonghoang
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Instructor;
import model.Student;

public class AccountDBContext extends DBContext {

    public Account getAccount(String username, String password) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [username], [password] from [Account] where username = ? and password = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account(rs.getString(1), rs.getString(2));
                return account;
            }
        } catch (SQLException ex) {
            System.out.println("loi lay ra account");
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    public Student getStudentFromAccount(String accountId){
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "select s.studentId from account a join student s on a.username = s.accountId where a.username = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, accountId);
            rs = stm.executeQuery();
            if (rs.next()) {
                Student s = Student.builder().studentId(rs.getString("studentId")).build();
                return s;
            }
        } catch (SQLException ex) {
            System.out.println("loi lay ra account cua student");
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;            
    }
        public Instructor getInstructorFromAccount(String accountId){
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "select i.instructorId from account a join Instructor i on a.username = i.accountId where a.username = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, accountId);
            rs = stm.executeQuery();
            if (rs.next()) {
                Instructor i = Instructor.builder().instructorId(rs.getString("instructorId")).build();
                return i;
            }
        } catch (SQLException ex) {
            System.out.println("loi lay ra account cua instructor");
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;            
    }
    public int getNumberOfRoles(String username, String url) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(*) AS Total FROM Account a \n"
                    + "INNER JOIN Account_Role ar on a.username = ar.username \n"
                    + "INNER JOIN [Role] g on g.roleId = ar.roleId\n"
                    + "INNER JOIN [Role_Feature] rf on rf.roleId = g.roleId\n"
                    + "INNER JOIN [Feature] f on rf.featureId = f.featureId \n"
                    + "WHERE a.username = ? AND f.url = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, url);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            System.out.println("loi lay ra so luong role");
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println(new AccountDBContext().getStudentFromAccount("HE171073@fpt.edu.vn").getStudentId());
        System.out.println(new AccountDBContext().getInstructorFromAccount("sonnt5@fpt.edu.vn").getInstructorId());
        int num = new AccountDBContext().getNumberOfRoles("HE171073@fpt.edu.vn", "/student/timetable");
        System.out.println(num);
        int num2 = new AccountDBContext().getNumberOfRoles("sonnt5@fpt.edu.vn", "/student/timetable");
        System.out.println(num2);
    }

}
