package crud_cr.mvc_1.service;

import crud_cr.mvc_1.dto.ProjectRequest;
import crud_cr.mvc_1.exception.BadRequestException;
import crud_cr.mvc_1.model.*;
import crud_cr.mvc_1.repository.ProjectRepository;
import crud_cr.mvc_1.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ModelMapper modelMapper;

    private final TaskRepository taskRepository;

    @CacheEvict(value = {"projects", "project"}, allEntries = true)
    public Project create(ProjectRequest request, User owner) {
        Project project = modelMapper.map(request, Project.class);
        project.setOwner(owner);
        project.setStatus(ProjectStatus.PLANNED);
        return projectRepository.save(project);
    }

    @CacheEvict(value = {"projects", "project"}, allEntries = true)
    public Project update(Long id, ProjectRequest request, User owner) {
        Project project = getById(id, owner);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        return projectRepository.save(project);
    }

    @CacheEvict(value = {"projects", "project"}, allEntries = true)
    public void delete(Long id, User owner) {
        Project project = getById(id, owner);
        projectRepository.delete(project);
    }


    public List<Project> getMyProjects(User owner)
    {
        return projectRepository.findByOwner(owner);
    }

    @Cacheable(
            value = "project",
            key = "#id + '::' + #owner.id"
    )
    public Project getById(Long id, User owner) {
        return projectRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new BadRequestException("Project not found"));
    }


    @CacheEvict(value = {"projects", "project"}, allEntries = true)
    public void updateProjectStatus(Project project) {
        List<Task> tasks = taskRepository.findByProject(project);

        if (tasks.isEmpty()) {
            project.setStatus(ProjectStatus.PLANNED);
        } else if (tasks.stream().allMatch(t -> t.getStatus() == TaskStatus.DONE)) {
            project.setStatus(ProjectStatus.COMPLETED);
        } else {
            project.setStatus(ProjectStatus.IN_PROGRESS);
        }

        projectRepository.save(project);
    }



    public Page<Project> getMyProjects(User owner, Pageable pageable) {
        return projectRepository.findByOwner(owner, pageable);
    }



    @Cacheable(
            value = "projects",
            key = "#owner.id + '::' + #pageable.pageNumber + '::' + #pageable.pageSize + '::' + #pageable.sort + '::' + #keyword"
    )
    public Page<Project> searchProjects(
            User owner,
            String keyword,
            Pageable pageable
    ) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return projectRepository.findByOwner(owner, pageable);
        }

        return projectRepository.findByOwnerAndNameContainingIgnoreCase(
                owner,
                keyword,
                pageable
        );
    }












}
