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
public class Course {
    private String courseId;
    private String courseName;
    private ArrayList<Group> groups = new ArrayList<>();;
}
