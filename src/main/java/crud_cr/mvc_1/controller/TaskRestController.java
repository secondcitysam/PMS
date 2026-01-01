package crud_cr.mvc_1.controller;

import crud_cr.mvc_1.dto.TaskRequest;
import crud_cr.mvc_1.model.Task;
import crud_cr.mvc_1.model.TaskStatus;
import crud_cr.mvc_1.model.User;
import crud_cr.mvc_1.service.TaskService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskRestController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getTasks(
            @PathVariable Long projectId,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        return taskService.getTasks(projectId, user);
    }

    @PostMapping
    public Task createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskRequest request,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        return taskService.createTask(projectId, request, user);
    }

    @PatchMapping("/{taskId}/status")
    public Task updateStatus(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @RequestParam TaskStatus status,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        return taskService.updateStatus(taskId, status, user);
    }
}
