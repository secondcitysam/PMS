package crud_cr.mvc_1.events;

import crud_cr.mvc_1.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskEventListener {

    private final ProjectService projectService;

    @EventListener
    public void onTaskCreated(TaskCreatedEvent event)
    {
        projectService.updateProjectStatus(event.getTask().getProject());


    }


    @EventListener
    public void onTaskCompleted(TaskCompletedEvent event)
    {
        projectService.updateProjectStatus(event.getTask().getProject());
    }





}
