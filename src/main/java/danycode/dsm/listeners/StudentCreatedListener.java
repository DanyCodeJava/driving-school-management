package danycode.dsm.listeners;

import danycode.dsm.infra.StudentCreatedEvent;
import danycode.dsm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StudentCreatedListener implements ApplicationListener<StudentCreatedEvent> {
    private final UserService userService;

    @Override
    public void onApplicationEvent(StudentCreatedEvent event) {
        userService.createUserForStudent(event.getStudentId());
    }
}
