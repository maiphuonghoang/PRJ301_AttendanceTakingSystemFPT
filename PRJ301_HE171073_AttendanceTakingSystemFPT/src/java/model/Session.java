/**
 *
 * @author maiphuonghoang
 */
package model;

import java.sql.Date;
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
public class Session {
    private int sessionId;
    private String sessionName;
    private Date date;
    private TimeSlot slotId;
    private Instructor lecturerId;
    private Group groupId;
    private Room roomId;
    private boolean sessionStatus; 
      
}
