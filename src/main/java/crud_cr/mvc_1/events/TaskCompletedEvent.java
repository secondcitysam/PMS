package crud_cr.mvc_1.events;

import crud_cr.mvc_1.model.Task;
import org.springframework.context.ApplicationEvent;

public class TaskCompletedEvent extends ApplicationEvent {

    public TaskCompletedEvent(Task task)
    {
        super(task);
    }

    public Task getTask()
    {
        return (Task) getSource();
    }
}
