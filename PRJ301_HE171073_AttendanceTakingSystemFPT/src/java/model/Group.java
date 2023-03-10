/**
 *
 * @author maiphuonghoang
 */
package model;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Group {
    private int groupId;
    private String groupName;
    private Course courseId;
    private Instructor instructorId;
    private ArrayList<Session> sessions = new ArrayList<>();;
 
}
