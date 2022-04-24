package gs.springportfolio.services.projects;

import gs.springportfolio.dto.ProjectDTO;
import gs.springportfolio.models.Project;
import gs.springportfolio.models.Skill;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ProjectService {

    List<Project> getAllProjects();
    Project addNewProject(String title,
                          LocalDate startDate,
                          LocalDate endDate,
                          String description,
                          Set<Skill> projectSkills,
                          List<String> projectPhotosPaths);
    Project editProject(Long id, ProjectDTO projectDTO);
    Long deleteProject(Long id) throws Exception;

}
