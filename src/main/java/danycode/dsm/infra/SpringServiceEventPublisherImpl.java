package danycode.dsm.infra;

import danycode.dsm.service.ServiceEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SpringServiceEventPublisherImpl implements ServiceEventPublisher {
    private final ApplicationEventPublisher eventPublisher;
    @Override
    public void publishStudentCreated(Long studentId) {
        eventPublisher.publishEvent(new StudentCreatedEvent(this,studentId));
    }
}
