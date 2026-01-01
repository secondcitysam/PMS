package crud_cr.mvc_1.repository;

import crud_cr.mvc_1.model.Project;
import crud_cr.mvc_1.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByProject(Project project);
}
