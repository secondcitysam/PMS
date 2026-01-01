package crud_cr.mvc_1.service;

import crud_cr.mvc_1.dto.TaskRequest;
import crud_cr.mvc_1.events.TaskCompletedEvent;
import crud_cr.mvc_1.events.TaskCreatedEvent;
import crud_cr.mvc_1.exception.BadRequestException;
import crud_cr.mvc_1.model.*;
import crud_cr.mvc_1.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    public Task createTask(Long projectId, TaskRequest request, User user) {

        Project project = projectService.getById(projectId, user);

        Task task = modelMapper.map(request, Task.class);
        task.setProject(project);

        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        }

        if (task.getPriority() == null) {
            task.setPriority(TaskPriority.MEDIUM);
        }

        Task savedTask = taskRepository.save(task);

        // 🔔 Publish event
        eventPublisher.publishEvent(new TaskCreatedEvent(savedTask));

        return savedTask;
    }

    public List<Task> getTasks(Long projectId, User user) {
        Project project = projectService.getById(projectId, user);
        return taskRepository.findByProject(project);
    }

    public Task markDone(Long taskId, User user) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BadRequestException("Task not found"));

        // ownership enforced via project
        projectService.getById(task.getProject().getId(), user);

        task.setStatus(TaskStatus.DONE);

        Task saved = taskRepository.save(task);

        // 🔔 Publish completion event
        eventPublisher.publishEvent(new TaskCompletedEvent(saved));

        return saved;
    }


    public Task updateStatus(Long taskId, TaskStatus newStatus, User user) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BadRequestException("Task not found"));

        // Ownership enforced via project
        projectService.getById(task.getProject().getId(), user);

        task.setStatus(newStatus);

        Task savedTask = taskRepository.save(task);

        if (newStatus == TaskStatus.DONE) {
            eventPublisher.publishEvent(new TaskCompletedEvent(savedTask));
        }

        return savedTask;
    }



}
