package crud_cr.mvc_1.events.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskEventProducer {

    private static final String TOPIC = "task-events";

    private final KafkaTemplate<String,TaskEventPayload> kafkaTemplate;

    public void sendTaskEvent(TaskEventPayload payload)
    {
        kafkaTemplate.send(TOPIC,payload.getEventType(),payload);
    }


}
