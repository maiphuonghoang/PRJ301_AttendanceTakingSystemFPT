/**
 *
 * @author maiphuonghoang
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attend;
import model.Group;
import model.Session;
import model.Student;

public class AttendDBContext extends DBContext {

    public ArrayList<Attend> getNotTakeAttendsByeSession(int sessionId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Attend> attends = new ArrayList<>();
        try {
            String sql = "SELECT s.studentId, s.studentName, s.studentImage, \n"
                    + "ses.sessionId, ses.slotId, ses.groupId, g.groupName, ses.date,  ses.[sessionStatus],\n"
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
            System.out.println("loi lay ra tat ca cac student cua session chua take attend");

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
        ArrayList<Attend> attends = new AttendDBContext().getNotTakeAttendsByeSession(17);
        for (Attend attend : attends) {
            System.out.println(attend);
        }
//        ArrayList<Attend> attends = new AttendDBContext().getAllAttends(130);
//        for (Attend attend : attends) {
//            System.out.println(attend);
//        }
    }

    public ArrayList<Attend> insertTakedAttendsBySession(int sessionId, ArrayList<Attend> takedStatus) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Attend> attends = new ArrayList<>();

        try {
            String sql = "INSERT INTO Attend (studentId,  sessionId, [status], recordTime, comment) VALUES (?, ?,?,?,?)";

            stm = connection.prepareStatement(sql);

            for (int i = 0; i < takedStatus.size(); i++) {

                Date date = takedStatus.get(i).getRecordTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String formattedDateTime = formatter.format(date);

                stm.setString(1, takedStatus.get(i).getStudentId().getStudentId());
                stm.setInt(2, takedStatus.get(i).getSessionId().getSessionId());
                stm.setBoolean(3, takedStatus.get(i).isStatus());
                stm.setString(4, formattedDateTime);
                stm.setString(5, takedStatus.get(i).getComment());
                stm.executeUpdate();

            }

        } catch (SQLException ex) {
            System.out.println("loi khi insert");
            Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attends;
    }
    /*
    public ArrayList<Attend> getTakedAttends(int sessionId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Attend> attends = new ArrayList<>();
        try {
            String sql = "SELECT a.studentId, a.sessionId, a.status, a.recordTime, a.comment, ses.date, ses.groupId, g.groupName, ses.sessionStatus, s.studentName\n"
                    + "from Attend a JOIN Session ses on a.sessionId = ses.sessionId \n"
                    + "JOIN [Group] g On g.groupId = ses.groupId\n"
                    + "JOIN Student s On a.studentId = s.studentId\n"
                    + "where ses.sessionId = ?";
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
                        .build();
                Session ses = Session.builder()
                        .sessionId(rs.getInt("sessionId"))
                        .date(rs.getDate("date"))
                        .groupId(g)
                        .sessionStatus(rs.getBoolean("sessionStatus"))
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
            System.out.println("loi lay ra tat ca cac student da duoc take attend session");

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
     */
}
