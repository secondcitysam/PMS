package crud_cr.mvc_1.dto;

import crud_cr.mvc_1.model.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;

    private LocalDate startDate;

    private LocalDate endDate;

}
