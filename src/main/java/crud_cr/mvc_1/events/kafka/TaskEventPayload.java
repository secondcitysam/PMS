package crud_cr.mvc_1.events.kafka;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEventPayload {

    private String eventType;
    private Long taskId;
    private String taskTitle;
    private Long projectId;

}
