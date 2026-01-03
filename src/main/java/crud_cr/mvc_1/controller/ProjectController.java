package crud_cr.mvc_1.controller;

import crud_cr.mvc_1.dto.ProjectRequest;
import crud_cr.mvc_1.model.Project;
import crud_cr.mvc_1.model.User;
import crud_cr.mvc_1.service.ProjectService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;



    @GetMapping("/new")
    public String newProject(Model model) {
        model.addAttribute("projectRequest", new ProjectRequest());
        return "project-form";
    }

    @PostMapping
    public String createProject(
            @Valid @ModelAttribute ProjectRequest projectRequest,
            BindingResult result,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            return "project-form";
        }

        User user = (User) session.getAttribute("loggedInUser");
        projectService.create(projectRequest, user);
        return "redirect:/projects";
    }

    @GetMapping("/{id}")
    public String projectDashboard(
            @PathVariable Long id,
            HttpSession session,
            Model model
    ) {
        User user = (User) session.getAttribute("loggedInUser");
        Project project = projectService.getById(id, user);
        model.addAttribute("project", project);
        return "project-dashboard";
    }

    @GetMapping
    public String listProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String keyword,
            HttpSession session,
            Model model
    ) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Project> projectPage =
                projectService.searchProjects(user, keyword, pageable);

        model.addAttribute("projectPage", projectPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        model.addAttribute("keyword", keyword);

        return "projects";
    }



}
