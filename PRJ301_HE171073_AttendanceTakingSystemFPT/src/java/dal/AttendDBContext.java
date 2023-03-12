/**
 *
 * @author maiphuonghoang
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attend;
import model.Group;
import model.Session;
import model.Student;

public class AttendDBContext extends DBContext {

    public ArrayList<Attend> getAllAttends(int sessionId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Attend> attends = new ArrayList<>();
        try {
            String sql = "SELECT s.studentId, s.studentName, s.studentImage, \n"
                    + "ses.sessionId, ses.slotId, ses.groupId, g.groupName, ses.date, \n"
                    + "a.status, a.recordTime, ISNULL(a.comment, '') AS comment\n"
                    + "FROM Student s LEFT JOIN  Participate p On s.studentId =p.studentId\n"
                    + "LEFT JOIN [Group] g On g.groupId = p.groupId \n"
                    + "LEFT JOIN [Session] ses ON ses.groupId = g.groupId\n"
                    + "LEFT JOIN Attend a ON a.sessionId = ses.sessionId\n"
                    + "WHERE ses.sessionId = ? ";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, sessionId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Group g = Group.builder()
                        .groupId(rs.getInt("groupId"))
                        .groupName(rs.getString("groupName"))
                        .build();
                Student s = Student.builder()
                        .studentId(rs.getString("studentId"))
                        .studentName(rs.getString("studentName"))
                        .studentImage(rs.getString("studentImage"))
                        .build();
                Session ses = Session.builder()
                        .sessionId(rs.getInt("sessionId"))
                        .groupId(g)
                        .build();
                Attend a = Attend.builder()
                        .studentId(s)
                        .sessionId(ses)
                        .status(rs.getBoolean("status"))
                        .recordTime(rs.getDate("recordTime"))
                        .comment(rs.getString("comment"))
                        .build();
                attends.add(a);
            }
        } catch (Exception ex) {
            System.out.println("loi lay ra tat ca cac student addtend session");

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
        return attends;

    }
    public static void main(String[] args) {
        ArrayList<Attend> attends = new AttendDBContext().getAllAttends(17);
        for (Attend attend : attends) {
            System.out.println(attend);
        }
    }
}
