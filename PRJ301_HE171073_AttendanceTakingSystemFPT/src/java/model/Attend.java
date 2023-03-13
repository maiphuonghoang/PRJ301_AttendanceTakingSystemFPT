/**
 *
 * @author maiphuonghoang
 */

package model;

import java.sql.Date;
import java.sql.Timestamp;
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
public class Attend {
    private Student studentId;
    private Session sessionId;
    private boolean status;
    private Timestamp recordTime;   
    private String comment;
}
