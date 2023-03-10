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
public class Instructor {
    private String instructorId;
    private String instructorName;
    private String instructorImage;
    private ArrayList<Group> groups = new ArrayList<>();;
    private ArrayList<Session> sessions = new ArrayList<>();;
    
}
