/**
 *
 * @author maiphuonghoang
 */
package model;

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
public class Student {
    private String studentId;
    private String studentName;
    private String studentImage;
    
}
