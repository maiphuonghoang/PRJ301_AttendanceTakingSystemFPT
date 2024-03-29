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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Group;
import model.Instructor;
import model.Room;
import model.Session;
import model.TimeSlot;

public class SessionDBContext extends DBContext {

    public ArrayList<Session> getSessionsOfCourse(int groupId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            String sql = "select * from [Session] ses join [Group] g on ses.groupId = g.groupId  WHERE g.groupId = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, groupId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Session s = new Session();
                s.setSessionId(rs.getInt("sessionId"));

                Group g = new Group();
                g.setGroupId(rs.getInt("groupId"));
                s.setGroupId(g);
                s.setDate(rs.getDate("date"));

                sessions.add(s);

            }
        } catch (Exception ex) {
            System.out.println("loi lay ra tat ca cac sessions");

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
        return sessions;

    }

    public ArrayList<Session> getSessionInstructorByWeek(String lecturerId, Date from, Date to) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            String sql = "SELECT i.instructorId, ses.date, ses.slotId, t.slotNumber, t.startTime, t.endTime, g.groupId, g.courseId, g.groupName, sessionId, ses.roomId, ses.sessionStatus  \n"
                    + "FROM Instructor i  JOIN [Session] ses ON i.instructorId = ses.lecturerId\n"
                    + "				   JOIN [Group] g ON g.groupId = ses.groupId \n"
                    + "				   JOIN TimeSlot t ON ses.slotId = t.slotId\n"
                    + "WHERE i.instructorId = ?\n"
                    + "AND ses.date BETWEEN ? AND ? ORDER BY ses.date";
            stm = connection.prepareStatement(sql);
            stm.setString(1, lecturerId);
            stm.setDate(2, from);
            stm.setDate(3, to);
            rs = stm.executeQuery();
            while (rs.next()) {
                Session s = new Session();
                s.setSessionId(rs.getInt("sessionId"));
                s.setSessionStatus(rs.getBoolean("sessionStatus"));

                Room r = new Room();
                r.setRoomId(rs.getString("roomId"));
                s.setRoomId(r);

                Instructor i = new Instructor();
                i.setInstructorId(rs.getString("instructorId"));
                s.setLecturerId(i);

                TimeSlot t = new TimeSlot();
                t.setSlotNumber(rs.getInt("slotNumber"));
                t.setSlotId(rs.getInt("slotId"));
                t.setStartTime(rs.getTime("startTime"));
                t.setEndTime(rs.getTime("endTime"));
                s.setSlotId(t);

                Course c = new Course();
                c.setCourseId(rs.getString("courseId"));

                Group g = new Group();
                g.setGroupId(rs.getInt("groupId"));
                g.setGroupName(rs.getString("groupName"));
                g.setCourseId(c);
                s.setGroupId(g);
                s.setDate(rs.getDate("date"));

                sessions.add(s);

            }
        } catch (Exception ex) {
            System.out.println("loi lay ra cac sessions by week");

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
        return sessions;
    }

    public void updateSessionStatus(int sessionId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "UPDATE [Session] SET [sessionStatus] = ? WHERE sessionId = ? ";
            stm = connection.prepareStatement(sql);
            stm.setBoolean(1, true);
            stm.setInt(2, sessionId);
            stm.executeUpdate();
        } catch (Exception ex) {
            System.out.println("loi update session");

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

    }

    public ArrayList<Session> getSessionsOfStudent(String studentId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Session> sessions = new ArrayList<>();
        try {
            String sql = "SELECT  stu.studentId, g.groupId, g.groupName, g.courseId, c.courseName\n"
                    + "FROM Student stu \n"
                    + "JOIN Participate p on stu.studentId = p.studentId\n"
                    + "JOIN [Group] g on g.groupId = p.groupId\n"
                    + "JOIN Course c on c.courseId = g.courseId WHERE stu.studentId = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, studentId);
            rs = stm.executeQuery();
            while (rs.next()) {
                Session s = new Session();

                Course c = Course.builder()
                        .courseId(rs.getString("courseId"))
                        .courseName(rs.getString("courseName"))
                        .build();

                Group g = new Group();
                g.setGroupId(rs.getInt("groupId"));
                g.setGroupName(rs.getString("groupName"));
                g.setCourseId(c);
                s.setGroupId(g);

                sessions.add(s);

            }
        } catch (Exception ex) {
            System.out.println("loi lay ra tat ca cac sessions");

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
        return sessions;

    }

    public static void main(String[] args) {
//        List<Session> list3 = new SessionDBContext().getSessionsOfCourse(15);
//        for (Session session : list3) {
//            System.out.println(session);
//        }
        List<Session> list3 = new SessionDBContext().getSessionsOfStudent("HE171073");
        for (Session session : list3) {
            System.out.println(session);
        }

//        List<Session> list2 = new SessionDBContext().getSessionInstructorByWeek("sonnt5", Date.valueOf("2023-02-20"), Date.valueOf("2023-02-26"));
//        for (Session session : list2) {
//            System.out.println(session);
//        }
//        List<Session> list1 = new SessionDBContext().getAllSessions();
//        for (Session session : list1) {
//            System.out.println(session);
//        }
    }

}
