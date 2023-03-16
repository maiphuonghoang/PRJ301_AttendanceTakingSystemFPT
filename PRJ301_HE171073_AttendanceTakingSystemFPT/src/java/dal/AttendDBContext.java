/**
 *
 * @author maiphuonghoang
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attend;
import model.Course;
import model.Group;
import model.Instructor;
import model.Room;
import model.Session;
import model.Student;
import model.TimeSlot;

public class AttendDBContext extends DBContext {

    public ArrayList<Attend> getNotTakeAttendsByeSession(int sessionId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Attend> attends = new ArrayList<>();
        try {
            String sql = "SELECT s.studentId, s.studentName, s.studentImage, \n"
                    + "ses.sessionId, ses.slotId, ses.groupId, g.groupName, ses.date,  ses.[sessionStatus],\n"
                    + "a.status, a.recordTime, ISNULL(a.comment, '') AS comment\n"
                    + "FROM Student s JOIN  Participate p On s.studentId =p.studentId\n"
                    + "JOIN [Group] g On g.groupId = p.groupId \n"
                    + "JOIN [Session] ses ON ses.groupId = g.groupId\n"
                    + "LEFT JOIN Attend a ON a.sessionId = ses.sessionId AND a.studentId = s.studentId\n"
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

    public void updateTakedAttendsBySession(int sessionId, ArrayList<Attend> changeattends) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "UPDATE [Attend] SET [status] = ?, [recordTime] = ? ,[comment] = ? WHERE sessionId = ? AND  [studentId] = ?";

            stm = connection.prepareStatement(sql);

            for (int i = 0; i < changeattends.size(); i++) {
                stm.setString(5, changeattends.get(i).getStudentId().getStudentId());
                stm.setBoolean(1, changeattends.get(i).isStatus());
                stm.setTimestamp(2, changeattends.get(i).getRecordTime());
                stm.setString(3, changeattends.get(i).getComment());
                stm.setInt(4, sessionId);
                stm.executeUpdate();

            }

        } catch (SQLException ex) {
            System.out.println("loi khi update session da diem danh");
            Logger.getLogger(AttendDBContext.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    public ArrayList<Attend> getReportAttendsOfGroup(int groupId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Attend> attends = new ArrayList<>();

        try {
            String sql = "EXEC Report_Attend ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, groupId);
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
                        .date(rs.getDate("date"))
                        .sessionStatus(rs.getBoolean("sessionStatus"))
                        .groupId(g)
                        .build();
                Attend a = Attend.builder()
                        .studentId(s)
                        .sessionId(ses)
                        .status(rs.getBoolean("status"))
                        .build();
                attends.add(a);
            }

        } catch (Exception ex) {
            System.out.println("loi lay ra tat ca cac student trong 1 mon cua 1 group");

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

    public LinkedHashMap<Student, Float> getReportAbsentPercentage(int groupId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        LinkedHashMap<Student, Float> attends = new LinkedHashMap<>();

        try {
            String sql = "EXEC Percent_Absent  ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, groupId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Student s = Student.builder()
                        .studentId(rs.getString("studentId"))
                        .studentName(rs.getString("studentName"))
                        .build();

                attends.put(s, rs.getFloat("percentAbsent"));
            }

        } catch (Exception ex) {
            System.out.println("loi lay ra tat ca cac student trong 1 mon cua 1 group");

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

    public ArrayList<Attend> getSessionStudentByWeek(String studentId, Date from, Date to) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Attend> attends = new ArrayList<>();
        try {
            String sql = "SELECT  stu.studentId, ses.groupId, g.groupName, g.courseId,\n"
                    + "ses.date,  ses.[sessionStatus], ses.roomId, ses.sessionId,\n"
                    + "t.slotNumber, t.startTime, t.endTime, a.status \n"
                    + "FROM Student stu \n"
                    + "JOIN Participate p on stu.studentId = p.studentId\n"
                    + "JOIN [Group] g on g.groupId = p.groupId\n"
                    + "JOIN [Session] ses on ses.groupId = g.groupId\n"
                    + "LEFT JOIN TimeSlot t ON ses.slotId = t.slotId \n"
                    + "LEFT JOIN Room r ON r.roomId = ses.roomId\n"
                    + "LEFT JOIN Attend a on a.studentId = stu.studentId AND a.sessionId = ses.sessionId\n"
                    + "WHERE stu.studentId = ?\n"
                    + "AND ses.date BETWEEN ? AND ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, studentId);
            stm.setDate(2, from);
            stm.setDate(3, to);
            rs = stm.executeQuery();
            while (rs.next()) {

                Room r = Room.builder()
                        .roomId(rs.getString("roomId"))
                        .build();

                TimeSlot t = TimeSlot.builder()
                        .slotNumber(rs.getInt("slotNumber"))
                        .startTime(rs.getTime("startTime"))
                        .endTime(rs.getTime("endTime"))
                        .build();
                Course c = Course.builder()
                        .courseId(rs.getString("courseId"))
                        .build();
                Group g = Group.builder()
                        .groupId(rs.getInt("groupId"))
                        .groupName(rs.getString("groupName"))
                        .courseId(c)
                        .build();

                Session s = Session.builder()
                        .sessionId(rs.getInt("sessionId"))
                        .roomId(r)
                        .slotId(t)
                        .date(rs.getDate("date"))
                        .sessionStatus(rs.getBoolean("sessionStatus"))
                        .groupId(g)
                        .build();
                Attend a = Attend.builder()
                        .status(rs.getBoolean("status"))
                        .sessionId(s)
                        .build();
                attends.add(a);

            }
        } catch (Exception ex) {
            System.out.println("loi lay ra cac sessions by week của student");

        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return attends;
    }

    public static void main(String[] args) {
//         AttendDBContext db = new AttendDBContext();
//        LinkedHashMap<Student, Float> attends4 = db.getReportAbsentPercentage("SE1638-NET", "PRN221");
//        for (HashMap.Entry<Student, Float> entry : attends4.entrySet()) {
//            System.out.println(entry.getKey()+ " " + entry.getValue());
//        }
        ArrayList<Attend> attends5 = new AttendDBContext().getSessionStudentByWeek("HE171073", Date.valueOf("2023-02-06"), Date.valueOf("2023-02-12"));
        for (Attend attend : attends5) {
            System.out.println(attend);
        }
//        ArrayList<Attend> attends3 = new AttendDBContext().getReportAttendsOfGroup("SE1638-NET", "PRN221");
//        for (Attend attend : attends3) {
//            System.out.println(attend);
//        }
//        ArrayList<Attend> attends2 = new AttendDBContext().getTakedAttendsBySession(76);
//        for (Attend attend : attends2) {
//            System.out.println(attend);
//        }
//        ArrayList<Attend> attends1 = new AttendDBContext().getNotTakeAttendsByeSession(17);
//        for (Attend attend : attends1) {
//            System.out.println(attend);
//        }
    }
}
