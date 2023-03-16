/**
 *
 * @author maiphuonghoang
 */
package model;

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
public class Room {
    private String roomId;
    private ArrayList<Session> sessions = new ArrayList<>();;


}
