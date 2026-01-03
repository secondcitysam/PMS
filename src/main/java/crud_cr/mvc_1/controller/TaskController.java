package crud_cr.mvc_1.controller;

import crud_cr.mvc_1.dto.TaskRequest;
import crud_cr.mvc_1.model.Task;
import crud_cr.mvc_1.model.TaskStatus;
import crud_cr.mvc_1.model.User;
import crud_cr.mvc_1.service.TaskService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Controller
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String viewTasks(
            @PathVariable Long projectId,
            HttpSession session,
            Model model
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<Task> tasks = taskService.getTasks(projectId, user);

        Map<TaskStatus, List<Task>> groupedTasks =
                tasks.stream().collect(Collectors.groupingBy(Task::getStatus));

        model.addAttribute("projectId", projectId);
        model.addAttribute("tasksByStatus", groupedTasks);
        model.addAttribute("taskRequest", new TaskRequest());

        return "tasks-kanban";
    }

    @PostMapping
    public String createTask(
            @PathVariable Long projectId,
            @Valid @ModelAttribute("taskRequest") TaskRequest request,
            BindingResult result,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "redirect:/projects/" + projectId + "/tasks";
        }

        taskService.createTask(projectId, request, user);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    @PostMapping("/{taskId}/start")
    public String moveToInProgress(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        taskService.updateStatus(taskId, TaskStatus.IN_PROGRESS, user);
        return "redirect:/projects/" + projectId + "/tasks";
    }

    @PostMapping("/{taskId}/done")
    public String moveToDone(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        taskService.updateStatus(taskId, TaskStatus.DONE, user);
        return "redirect:/projects/" + projectId + "/tasks";
    }
}
