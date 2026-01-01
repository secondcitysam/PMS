package crud_cr.mvc_1.repository;

import crud_cr.mvc_1.model.Project;
import crud_cr.mvc_1.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findByOwner(User owner);

    Optional<Project> findByIdAndOwner(Long id,User owner);

    Page<Project> findByOwner(User owner, Pageable pageable);

    Page<Project> findByOwnerAndNameContainingIgnoreCase(
            User owner,
            String keyword,
            Pageable pageable
    );
}
