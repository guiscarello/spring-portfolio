package gs.springportfolio.dto;

import gs.springportfolio.models.ProjectPhoto;
import gs.springportfolio.models.Skill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor
public class ProjectDTO {

    private Long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Set<Skill> skills = null;
    private List<ProjectPhoto> photos = null;

}
