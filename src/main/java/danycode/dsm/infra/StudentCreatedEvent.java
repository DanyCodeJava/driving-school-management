package danycode.dsm.infra;

import lombok.*;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
@ToString
public class StudentCreatedEvent extends ApplicationEvent {
   private final long studentId;

    public StudentCreatedEvent(Object source,long studentId) {
        super(source);
        this.studentId = studentId;
    }
}
