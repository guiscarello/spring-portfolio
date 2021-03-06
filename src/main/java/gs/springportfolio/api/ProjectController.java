package gs.springportfolio.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gs.springportfolio.dto.ProjectDTO;
import gs.springportfolio.models.Project;
import gs.springportfolio.models.Skill;
import gs.springportfolio.services.files.FirebaseImageFileManagerService;
import gs.springportfolio.services.files.LocalImageFileManagerServiceImpl;
import gs.springportfolio.services.projects.FirebaseProjectServiceImpl;
import gs.springportfolio.services.projects.LocalProjectServiceImpl;
import gs.springportfolio.services.skills.FirebaseSkillServiceImpl;
import gs.springportfolio.services.skills.LocalSkillServiceImpl;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@RequestMapping(path = "/api")
@RestController
public class ProjectController {

    //private final LocalProjectServiceImpl projectService;
    //private final LocalSkillServiceImpl skillService;
    //private final LocalImageFileManagerServiceImpl photoFileManagerService;
    private final FirebaseProjectServiceImpl projectService;
    private final FirebaseSkillServiceImpl skillService;
    private final FirebaseImageFileManagerService firebaseImageFileManagerService;
    @Value("${application.project.photos.upload.folder}")
    private String uploadFolder;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    public ProjectController(
            FirebaseProjectServiceImpl projectService,
            FirebaseSkillServiceImpl skillService,
            FirebaseImageFileManagerService firebaseImageFileManagerService
            //LocalProjectServiceImpl projectService,
           // LocalSkillServiceImpl skillService,
            //LocalImageFileManagerServiceImpl photoFileManagerService
            ) {
        this.projectService = projectService;
        this.skillService = skillService;
        this.firebaseImageFileManagerService = firebaseImageFileManagerService;
    }

    @GetMapping(path = "/projects")
    public ResponseEntity<List<Project>> getProjects(){
        List<Project> ps = this.projectService.getAllProjects();

        return ResponseEntity.ok(ps);
    }

    @PostMapping(path = "/projects",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Project> addProject(
            @RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam("additionalImages[]") MultipartFile[] additionalImages,
            @RequestParam("title") String title,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("description") String description,
            @RequestParam("skillsIds[]") String skillsIds
    ) throws JsonProcessingException {
        //List<Long> ids = Arrays.stream(skillsIds).collect(Collectors.toList()); <--Postman, skillsIds needs to be Long[]
        ObjectMapper mapper = new ObjectMapper();
        List<Long> ids = mapper.readValue(skillsIds, new TypeReference<>() {});
        log.info("Skill ids: {}", ids);
        List<MultipartFile> photos = Arrays.stream(additionalImages).collect(Collectors.toList());
        photos.add(0, mainImage);


        Set<Skill> projectSkills = this.skillService.getSkillsByIds(ids);

        List<String> projectPhotosPaths= this.firebaseImageFileManagerService.uploadMultipleFiles(photos, uploadFolder);
        return ResponseEntity.ok(projectService.addNewProject(
                title,
                LocalDate.parse(startDate, this.formatter),
                LocalDate.parse(endDate, this.formatter),
                description,
                projectSkills,
                projectPhotosPaths
            )
        );
    }

    @PutMapping(path = "/projects/{id}")
    public ResponseEntity<Project> editProject(
            @PathVariable Long id,
            /*
            **TODO: edition of images, for now images cannot be edit
            *  https://www.baeldung.com/spring-jpa-soft-delete
             */
            /*@RequestParam("mainImage") MultipartFile mainImage,
            @RequestParam("additionalImages[]") MultipartFile[] additionalImages,*/
            @RequestParam("title") String title,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("description") String description,
            @RequestParam("skillsIds[]") String skillsIds
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Long> ids = mapper.readValue(skillsIds, new TypeReference<>() {});
        Set<Skill> projectSkills = this.skillService.getSkillsByIds(ids);

        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setTitle(title);
        projectDTO.setStartDate(LocalDate.parse(startDate, this.formatter));
        projectDTO.setEndDate(LocalDate.parse(endDate, this.formatter));
        projectDTO.setDescription(description);
        projectDTO.setSkills(projectSkills);

        return ResponseEntity.ok(this.projectService.editProject(id, projectDTO));
    }

    @DeleteMapping(path = "/projects/{id}")
    public ResponseEntity<Long> deleteProject(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(this.projectService.deleteProject(id));
    }
}
