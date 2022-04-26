package gs.springportfolio.services.projects;

import gs.springportfolio.dto.ProjectDTO;
import gs.springportfolio.models.Project;
import gs.springportfolio.models.Skill;
import gs.springportfolio.models.ProjectPhoto;
import gs.springportfolio.repos.ProjectPhotoRepo;
import gs.springportfolio.repos.ProjectRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LocalProjectServiceImpl implements ProjectService{

    private final ProjectRepo projectRepo;
    private final ProjectPhotoRepo projectPhotoRepo;
    @Value("${app.server}")
    private String server;
    @Value("${spring.port}")
    private String port;

    public LocalProjectServiceImpl(ProjectRepo projectRepo, ProjectPhotoRepo projectPhotoRepo) {
        this.projectRepo = projectRepo;
        this.projectPhotoRepo = projectPhotoRepo;
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = this.projectRepo.findAll();
        projects.forEach(project -> {
            List<ProjectPhoto> projectPhotos = project.getPhotos();
            projectPhotos.forEach(projectPhoto -> {
                projectPhoto.setProjectPhotoPath(
                        this.createPathToFile(projectPhoto.getProjectPhotoPath())
                );
            });

        });
        return projects;
    }

    @Override
    public Project addNewProject(String title,
                                 LocalDate startDate,
                                 LocalDate endDate,
                                 String description,
                                 Set<Skill> projectSkills,
                                 List<String> projectPhotosPaths) {
        List<ProjectPhoto> projectPhotos = new ArrayList<>();
        projectPhotosPaths.forEach(path -> {
            projectPhotos.add(new ProjectPhoto(
                path,
                projectPhotosPaths.indexOf(path) == 0

            ));
        });
        Project project = new Project(
                title,
                startDate,
                endDate,
                description,
                projectSkills,
                projectPhotos
        );
        Project newProject = this.projectRepo.save(project);
        projectPhotos.forEach(projectPhoto -> projectPhoto.setProject(newProject));
        List<ProjectPhoto> photos = this.projectPhotoRepo.saveAll(projectPhotos);
        photos.forEach(projectPhoto -> {
            projectPhoto.setProjectPhotoPath(
                   this.createPathToFile(projectPhoto.getProjectPhotoPath())
            );
        });
        newProject.setPhotos(photos);
        return newProject;
    }

    @Override
    public Project editProject(Long id, ProjectDTO projectDTO) {
        Project project = this.projectRepo
                .findById(id)
                .orElseThrow();
        /*
         **TODO: Add logic for images editing if not null
         *  https://www.baeldung.com/spring-jpa-soft-delete
         */
        project.setTitle(projectDTO.getTitle());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setDescription(projectDTO.getDescription());
        project.setSkills(projectDTO.getSkills());

        Project editedProject = this.projectRepo.save(project);

        editedProject.getPhotos().forEach(projectPhoto -> {
            projectPhoto.setProjectPhotoPath(
                    this.createPathToFile(projectPhoto.getProjectPhotoPath())
            );
        });

        return editedProject;
    }

    @Override
    public Long deleteProject(Long id) {
        Project projectToDelete = this.projectRepo.getById(id);
        projectToDelete.getSkills().forEach(
                skill -> {
                    projectToDelete.removeSkill(skill);
                }
        );
        this.projectRepo.save(projectToDelete);
        this.projectRepo.deleteById(id);
        return id;
    }

    private String createPathToFile(String projectPhotoPath){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(this.server)
                .append(":")
                .append(this.port)
                .append(projectPhotoPath);
        return stringBuilder.toString();
    }
}
