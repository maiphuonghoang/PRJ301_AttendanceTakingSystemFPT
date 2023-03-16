/**
 *
 * @author maiphuonghoang
 */
package model;

import java.sql.Time;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TimeSlot {
    private int slotId;
    private int slotNumber;
    private Time startTime;
    private Time endTime;
    private ArrayList<Session> sessions = new ArrayList<>();;

}
