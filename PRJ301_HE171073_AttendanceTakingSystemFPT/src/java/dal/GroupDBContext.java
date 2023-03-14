/**
 *
 * @author maiphuonghoang
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Group;


public class GroupDBContext extends DBContext{
        public ArrayList<Group> getGroupsByIntructor(String instructorId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Group> groups = new ArrayList<>();
        try {
            String sql = "SELECT groupId, groupName, courseId FROM [Group] WHERE instructorId = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, instructorId);
            rs = stm.executeQuery();
            while (rs.next()) {   
                Course c = Course.builder().courseId(rs.getString("courseId")).build();
                Group g = Group.builder()
                        .groupId(rs.getInt("groupId"))
                        .groupName(rs.getString("groupName"))
                        .courseId(c)
                        .build();
                groups.add(g);

            }
            return groups;
        } catch (Exception ex) {
            System.out.println("loi lay ra tat ca cac group cua giang vien");

        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return groups;

    }
        public ArrayList<Group> getGroupsByCourse(String instructorId) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        ArrayList<Group> groups = new ArrayList<>();
        try {
            String sql = "SELECT groupId, groupName, courseId FROM [Group] WHERE courseId = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, instructorId);
            rs = stm.executeQuery();
            while (rs.next()) {   
                Course c = Course.builder().courseId(rs.getString("courseId")).build();
                Group g = Group.builder()
                        .groupId(rs.getInt("groupId"))
                        .groupName(rs.getString("groupName"))
                        .courseId(c)
                        .build();
                groups.add(g);

            }
            return groups;
        } catch (Exception ex) {
            System.out.println("loi lay ra tat ca cac group cua giang vien");

        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return groups;

    }
                
        public static void main(String[] args) {
        List<Group> groups = new GroupDBContext().getGroupsByIntructor("sonnt5");
            for (Group group : groups) {
                System.out.println(group);
            }
    }
}
