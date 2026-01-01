package crud_cr.mvc_1.events;

import crud_cr.mvc_1.model.Task;
import org.springframework.context.ApplicationEvent;

public class TaskCreatedEvent extends ApplicationEvent {


    public TaskCreatedEvent(Task task) {
        super(task);
    }

    public Task getTask()
    {
        return (Task) getSource();
    }
}
