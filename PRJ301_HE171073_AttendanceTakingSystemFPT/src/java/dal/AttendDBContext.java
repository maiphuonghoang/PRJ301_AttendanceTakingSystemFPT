/**
 *
 * @author maiphuonghoang
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attend;
import model.Group;
import model.Instructor;
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
                        .recordTime(rs.getTimestamp("recordTime"))
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

    public ArrayList<Attend> insertTakedAttendsBySession(int sessionId, ArrayList<Attend> takedStatus) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Attend> attends = new ArrayList<>();

        try {
            String sql = "INSERT INTO Attend (studentId,  sessionId, [status], recordTime, comment) VALUES (?, ?,?,?,?)";

            stm = connection.prepareStatement(sql);

            for (int i = 0; i < takedStatus.size(); i++) {
                stm.setString(1, takedStatus.get(i).getStudentId().getStudentId());
                stm.setInt(2, takedStatus.get(i).getSessionId().getSessionId());
                stm.setBoolean(3, takedStatus.get(i).isStatus());
                stm.setTimestamp(4, takedStatus.get(i).getRecordTime());
                stm.setString(5, takedStatus.get(i).getComment());
                stm.executeUpdate();

            }

        } catch (SQLException ex) {
            System.out.println("loi khi insert");
            Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return attends;
    }

    public ArrayList<Attend> getTakedAttendsBySession(int sessionId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Attend> attends = new ArrayList<>();
        try {
            String sql = "SELECT s.studentId, s.studentName, s.studentImage,\n"
                    + "ses.sessionId, ses.groupId, g.groupName, ses.date, ses.lecturerId,\n"
                    + "a.status, a.recordTime, ISNULL(a.comment, '') AS comment\n"
                    + "FROM Student s JOIN  Participate p On s.studentId =p.studentId\n"
                    + "JOIN [Group] g On g.groupId = p.groupId \n"
                    + "JOIN [Session] ses ON ses.groupId = g.groupId\n"
                    + "JOIN Instructor i ON ses.lecturerId = i.instructorId\n"
                    + "JOIN Attend a ON a.studentId = s.studentId\n"
                    + "AND a.sessionId = ses.sessionId \n"
                    + "WHERE ses.sessionId = ?";
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
                Instructor i = Instructor.builder()
                        .instructorId(rs.getString("lecturerId")).build();
                Session ses = Session.builder()
                        .sessionId(rs.getInt("sessionId"))
                        .date(rs.getDate("date"))
                        .groupId(g)
                        .lecturerId(i)
                        .build();
                Attend a = Attend.builder()
                        .studentId(s)
                        .sessionId(ses)
                        .status(rs.getBoolean("status"))
                        .recordTime(rs.getTimestamp("recordTime"))
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

    public static void main(String[] args) {

        ArrayList<Attend> attends2 = new AttendDBContext().getTakedAttendsBySession(2);
        for (Attend attend : attends2) {
            System.out.println(attend);
        }
//        ArrayList<Attend> attends1 = new AttendDBContext().getNotTakeAttendsByeSession(17);
//        for (Attend attend : attends1) {
//            System.out.println(attend);
//        }

    }
}
